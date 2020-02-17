package com.example.im;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.im.adapter.MainViewPagerAdapter;
import com.example.im.utils.ImageManager;
import com.example.im.view.ChatsLayout;
import com.example.im.view.ContactsLayout;
import com.example.im.view.MomentsLayout;
import com.hdl.elog.ELog;

import java.io.FileDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class MainPage extends AppCompatActivity {
    private Context context;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private DrawerLayout drawerLayout;
    private List<TabLayout.Tab> tabList;

    private JWebSocketClient client;
    private JWebSocketClientService.JWebSocketClientBinder binder;
    private JWebSocketClientService jWebSocketClientService;

    private MessageReceiver messageReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainPage.this;

        // start service
        startJWebSocketClientService();
        // bind service
        bindService();
        // register broadcast
        doRegisterReceiver();
        // notification
        checkNotificationIsOpen(context);
        initView();
    }

    // Start Websocket Client Service
    private void startJWebSocketClientService() {
        Intent intent = new Intent(context, JWebSocketClientService.class);
        startService(intent);
    }

    // Bind Service
    private void bindService() {
        Intent bindIntent = new Intent(context, JWebSocketClientService.class);
        bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE);
    }

    // Register Notification Dynamically
    private void doRegisterReceiver() {
        messageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter("com.example.im.servicecallback.content");
        registerReceiver(messageReceiver, filter);
    }

    // Check Notification
    private void checkNotificationIsOpen(final Context context) {
        if (!isNotificationEnabled(context)) {
            new AlertDialog.Builder(context).setTitle("Tips")
                    .setMessage("You havn't allow notification, do you want to open now?")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            setNotification(context);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) { }
                    }).show();
        }
    }

    private void setNotification(Context context) {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            intent.setAction("android.setting.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", context.getPackageName());
            intent.putExtra("app_uid", context.getApplicationInfo().uid);
        }
        else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("package:" + context.getPackageName()));
        }
        else {

        }
        context.startActivity(intent);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private boolean isNotificationEnabled(Context context) {
        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

        AppOpsManager appOpsManager = (AppOpsManager)context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = applicationInfo.uid;

        Class appOpsClass = null;
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(appOpsManager, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void initView() {
        getSupportActionBar().hide();

        viewPager = findViewById(R.id.mainViewPager);
        tabLayout = findViewById(R.id.mainTabLayout);

        tabList = new ArrayList<>();

        // Set ViewPager Adapter
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mainViewPagerAdapter.addItem(new ChatsLayout());
        mainViewPagerAdapter.addItem(new ContactsLayout());
        mainViewPagerAdapter.addItem(new MomentsLayout());

        viewPager.setAdapter(mainViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabList.add(tabLayout.getTabAt(0));
        tabList.add(tabLayout.getTabAt(1));
        tabList.add(tabLayout.getTabAt(2));

        tabList.get(0).setIcon(R.drawable.msgselected).setText("Chats");
        tabList.get(1).setIcon(R.drawable.contactsunselected).setText("Contacts");
        tabList.get(2).setIcon(R.drawable.momentunselected).setText("Moments");

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabList.get(tab.getPosition()).setIcon(ImageManager.tabImageID[tab.getPosition() + 3]);
                tabLayout.setTabTextColors(ContextCompat.getColor(MainPage.this, R.color.colorBlack),
                        ContextCompat.getColor(MainPage.this, R.color.colorBlue));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tabList.get(tab.getPosition()).setIcon(ImageManager.tabImageID[tab.getPosition()]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    private ServiceConnection serviceConnection =  new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ELog.e("MainPage", "Service bind Activity successfully");
            binder = (JWebSocketClientService.JWebSocketClientBinder)iBinder;
            jWebSocketClientService = binder.getService();
            client = jWebSocketClientService.client;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            ELog.e("MainPage", "Service disconnect Activity");
        }
    };

    private class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ELog.e("MainPage", "Receive Message Broadcast");
        }
    }
}
