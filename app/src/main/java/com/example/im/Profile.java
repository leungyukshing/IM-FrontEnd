package com.example.im;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.im.view.TitleBar;

public class Profile extends AppCompatActivity {
    private TitleBar titleBar;
    private EditText username;
    private EditText sign;
    private EditText password;
    private EditText repeat_password;
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.profile);

        initView();
        setClickListener();
    }

    private void initView() {
        titleBar = findViewById(R.id.profile_titleBar);
        username = findViewById(R.id.profile_username);
        sign = findViewById(R.id.profile_sign);
        password = findViewById(R.id.profile_password);
        repeat_password = findViewById(R.id.profile_repeat_password);
        button = findViewById(R.id.profile_btn);
    }

    private void setClickListener() {
        titleBar.SetTitleBarClickListener(new TitleBar.TitleBarOnClickListener() {
            @Override
            public void leftButtonClick() {
                // Return
                finish();
            }

            @Override
            public void rightButtonClick() { }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Profile.this, "Submit Succeed!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
