package com.example.im;

import android.util.Log;

import com.hdl.elog.ELog;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;

public class JWebSocketClient extends WebSocketClient {
    public JWebSocketClient(URI uri) {
        super(uri, new Draft_6455());
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        ELog.e("JWebSocketClient", "onOpen()");
    }

    @Override
    public void onMessage(String message) {
        Log.e("JWebSocketClient", "onMessage()");
    }

    @Override
    public void onMessage(ByteBuffer bytes) {
        super.onMessage(bytes);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.e("JWebSocketClient", "onClose()");
    }

    @Override
    public void onError(Exception ex) {
        Log.e("JWebSocketClient", "onError()");
    }


}
