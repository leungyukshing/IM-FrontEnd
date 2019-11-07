package com.example.im;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonInfo extends AppCompatActivity {
    ImageView avatar;
    TextView username;
    TextView useremail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_info);
        initView();
    }
    private void initView() {
        avatar = findViewById(R.id.person_info_avatar);
        username = findViewById(R.id.person_info_username);
        useremail = findViewById(R.id.person_info_email);

        username.setText(getIntent().getStringExtra("username"));
        useremail.setText(getIntent().getStringExtra("useremail"));
    }
}
