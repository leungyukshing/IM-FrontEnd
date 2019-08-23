package com.example.im;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

public class Welcome extends AppCompatActivity {
    // const variables
    private static final int GO_GUIDE = 0;
    private static final int GO_HOME = 1;
    private static final int DELAY = 2000;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_GUIDE: {
                    goGuide(); // jump to GuidePage
                    break;
                }
                case GO_HOME: {
                    goHome(); // jump to LoginOrRegister
                    break;
                }
                default: break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initLoad();
    }


    private void initLoad() {
        SharedPreferences sharedPreferences = getSharedPreferences("im", MODE_PRIVATE);
        boolean goGuide = sharedPreferences.getBoolean("guide", true);
        if (!goGuide) {
            handler.sendEmptyMessageDelayed(GO_HOME, DELAY);
        }
        else {
            handler.sendEmptyMessageDelayed(GO_GUIDE, DELAY);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.apply();
        }
    }

    private void goGuide() {
        Intent intent = new Intent(this, GuidePage.class);
        startActivity(intent);
        finish();
    }

    private void goHome() {
        Intent intent = new Intent(this, MainPage.class);
        startActivity(intent);
        finish();
    }
}
