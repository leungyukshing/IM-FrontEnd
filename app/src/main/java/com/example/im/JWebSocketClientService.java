package com.example.im;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.hdl.elog.ELog;

import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;

public class JWebSocketClientService extends Service {
    public JWebSocketClient client;
    private JWebSocketClientBinder binder = new JWebSocketClientBinder();
    private final static int GRAY_SERVICE_ID = 1001;

    private PowerManager.WakeLock wakeLock;


    public JWebSocketClientService() {}

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        closeConnection();
        super.onDestroy();
    }

    private void initSocketClient() {
        // set up websocket connection
        URI uri = URI.create("ws://localhost:8080");
        client = new JWebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                super.onOpen(handshakedata);
                ELog.e("JWebSocketClientService", "websocket connect succeed");
            }

            @Override
            public void onMessage(ByteBuffer bytes) {
                ELog.e("JWebSocketClientService", "Receive Message");
                // super.onMessage(bytes);
                Intent intent = new Intent();
                intent.putExtra("message", bytes.array());
                sendBroadcast(intent);

                // Unmarshal Protobuf
                ImEntities.SendMessageRequest.Builder builder = ImEntities.SendMessageRequest.newBuilder();
                ImEntities.SendMessageRequest sendMessage = builder.build();
                try {
                    sendMessage = ImEntities.SendMessageRequest.parseFrom(bytes.array());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                checkLockAndShowNotification(sendMessage.getMessage());
            }
        };
        startConnection();
    }

    private void startConnection() {
        new Thread() {
            @Override
            public void run() {
                // wait->connect->send
                // report error if cannot connect
                try {
                    client.connectBlocking();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void closeConnection() {
        try {
            if (client != null) {
                client.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client = null;
        }
    }

    // get power lock, still use CPU when the monitor is dark
    @SuppressLint("InvalidWakeLockTag")
    private void acquireWakeLock() {
        if (wakeLock == null) {
            PowerManager powerManager = (PowerManager)this.getSystemService(Context.POWER_SERVICE);
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK|PowerManager.ON_AFTER_RELEASE, "PostLocationService");
            if (wakeLock != null) {
                wakeLock.acquire();
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initSocketClient();
        handler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE); // start heart beat detection
        // set service as foreground service, prioritize it
        if (Build.VERSION.SDK_INT < 18) {
            startForeground(GRAY_SERVICE_ID, new Notification());
        }
        else if (Build.VERSION.SDK_INT > 18 && Build.VERSION.SDK_INT < 25) {
            Intent innerIntent = new Intent(this, GrayInnerService.class);
            startService(innerIntent);
            startForeground(GRAY_SERVICE_ID, new Notification());
        }
        else {
            startForeground(GRAY_SERVICE_ID, new Notification());
        }
        acquireWakeLock();
        return START_STICKY;
    }

    public static class GrayInnerService extends Service {
        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            startForeground(GRAY_SERVICE_ID, new Notification());
            stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }

    // Binder between Activity and Service
    public class JWebSocketClientBinder extends Binder {
        public JWebSocketClientService getService() {
            return JWebSocketClientService.this;
        }
    }

    // Notify User
    // If the monitor is locked, first brighten the monitor
    private void checkLockAndShowNotification(String content) {
        KeyguardManager keyguardManager = (KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE);
        if (keyguardManager.inKeyguardRestrictedInputMode()) {
            // monitor is locked
            PowerManager powerManager = (PowerManager)this.getSystemService(Context.POWER_SERVICE);
            if (!powerManager.isScreenOn()) {
                @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = powerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP |
                        PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
                wl.acquire(); // brighten monitor
                wl.release(); // release the lock
            }
            sendNotification(content);
        }
        else {
            sendNotification(content);
        }
    }

    private void sendNotification(String content) {
        Intent intent = new Intent();
        intent.setClass(this, MainPage.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_MAX)
                .setContentTitle("IM")
                .setContentText(content)
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_VIBRATE|Notification.DEFAULT_ALL|Notification.DEFAULT_SOUND)
                .setContentIntent(pendingIntent)
                .build();
        notificationManager.notify(1, notification);
    }

    /* Websocket Heart Beat Rate Detection */
    private static final long HEART_BEAT_RATE = 10 * 1000;
    private Handler handler = new Handler();
    private Runnable heartBeatRunnable = new Runnable() {
        @Override
        public void run() {
            ELog.e("JWebSocketClientService", "Heart Beat Detection");
            if (client != null) {
                if (client.isClosed()) {
                    reconnect();
                }
            }
            else {
                initSocketClient();
            }
            handler.postDelayed(this, HEART_BEAT_RATE);
        }
    };

    // websocket reconnect
    private void reconnect() {
        handler.removeCallbacks(heartBeatRunnable);
        new Thread() {
            @Override
            public void run() {
                try {
                    ELog.e("JWebSocketClientService", "Websocket Reconnect");
                    client.reconnectBlocking();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
