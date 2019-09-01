package com.example.im;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.im.view.TitleBar;

public class Setting extends AppCompatActivity {
    private TitleBar titleBar;
    private ImageView guide;
    private ImageView password;
    private ImageView offline;

    private boolean guideMode = true;
    private boolean passwordMode = true;
    private boolean offlineMode = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.setting);

        initView();
        setClickListener();
    }

    private void initView() {
        titleBar = findViewById(R.id.setting_titleBar);
        guide = findViewById(R.id.setting_guide);
        password = findViewById(R.id.setting_password);
        offline = findViewById(R.id.setting_offline);

        guideMode = getSharedPreferences("im", MODE_PRIVATE).getBoolean("guide", true);
        guide.setImageResource(guideMode ? R.drawable.btnselected: R.drawable.btnunselected);
        passwordMode = getSharedPreferences("im", MODE_PRIVATE).getBoolean("password", true);
        password.setImageResource(passwordMode ? R.drawable.btnselected: R.drawable.btnunselected);
        offlineMode = getSharedPreferences("im", MODE_PRIVATE).getBoolean("offline", true);
        offline.setImageResource(offlineMode ? R.drawable.btnselected: R.drawable.btnunselected);

    }

    private void setClickListener() {
        titleBar.SetTitleBarClickListener(new TitleBar.TitleBarOnClickListener() {
            @Override
            public void leftButtonClick() {
                SharedPreferences sharedPreferences = getSharedPreferences("im", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("guide", guideMode);
                editor.putBoolean("password", passwordMode);
                editor.putBoolean("offline", offlineMode);
                editor.apply();
                finish();
            }

            @Override
            public void rightButtonClick() { }
        });

        guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (guideMode) {
                    guide.setImageResource(R.drawable.btnunselected);
                    guideMode = false;
                }
                else {
                    guide.setImageResource(R.drawable.btnselected);
                    guideMode = true;
                }
            }
        });

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (passwordMode) {
                    password.setImageResource(R.drawable.btnunselected);
                    passwordMode = false;
                }
                else {
                    password.setImageResource(R.drawable.btnselected);
                    passwordMode = true;
                }
            }
        });

        offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (offlineMode) {
                    offline.setImageResource(R.drawable.btnunselected);
                    offlineMode = false;
                }
                else {
                    offline.setImageResource(R.drawable.btnselected);
                    offlineMode = true;
                }
            }
        });
    }
}
