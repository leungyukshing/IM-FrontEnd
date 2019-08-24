package com.example.im;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;

public class LoginOrRegister extends AppCompatActivity implements View.OnClickListener {
    TabHost tabHost;
    Button loginBtn;
    EditText loginUsername;
    EditText loginPassword;

    Button registerBtn;
    EditText registerUsername;
    EditText registerPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_or_register);

        initView();
    }

    private void initView() {
        tabHost = findViewById(R.id.tabHost);

        loginBtn = findViewById(R.id.login_btn);
        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);

        registerBtn = findViewById(R.id.register_btn);
        registerUsername = findViewById(R.id.register_username);
        registerPassword = findViewById(R.id.register_password);

        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("Login").setIndicator("Login").setContent(R.id.login_layout));
        tabHost.addTab(tabHost.newTabSpec("Register").setIndicator("Register").setContent(R.id.register_layout));

        // Register Button Listener
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn: {
                String username = loginUsername.getText().toString();
                String password = loginPassword.getText().toString();
                if (login(username, password)) {
                    // Jump to MainPage
                    Intent intent = new Intent(this, MainPage.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    // reset
                    loginUsername.setText("");
                    loginPassword.setText("");
                }
                break;
            }
            case R.id.register_btn: {
                Intent intent = new Intent(this, MainPage.class);
                startActivity(intent);
                finish();
                break;
            }
            default:
                break;
        }
    }

    private boolean login(String username, String password) {
        // validate
        if (username == null || password == null || password.length() > 20) {
            return false;
        }

        // call to server
        return true;
    }
}
