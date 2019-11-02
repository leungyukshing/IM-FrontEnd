package com.example.im;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.hdl.elog.ELog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.disposables.Disposable;

public class LoginOrRegister extends AppCompatActivity implements View.OnClickListener {
    TabHost tabHost;
    Button loginBtn;
    EditText loginUsername;
    EditText loginPassword;

    Button registerBtn;
    EditText registerUsername;
    EditText registerPassword;
    EditText registerRepeatPassword;
    EditText registerEmail;

    TextView error;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_or_register);
        initView();
        HttpSend.getInstance().initContext(this);
    }

    private void initView() {
        tabHost = findViewById(R.id.tabHost);

        loginBtn = findViewById(R.id.login_btn);
        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);

        registerBtn = findViewById(R.id.register_btn);
        registerUsername = findViewById(R.id.register_username);
        registerPassword = findViewById(R.id.register_password);
        registerRepeatPassword = findViewById(R.id.register_repeatPassword);
        registerEmail = findViewById(R.id.register_email);

        error = findViewById(R.id.errormsg);

        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("Login").setIndicator("Login").setContent(R.id.login_layout));
        tabHost.addTab(tabHost.newTabSpec("Register").setIndicator("Register").setContent(R.id.register_layout));

        // Register Button Listener
        loginBtn.setOnClickListener(this);

        registerEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    // clear error
                    error.setText("");
                }
                else {
                    // validate email format
                    String email = registerEmail.getText().toString();
                    if (!validateEmail(email)) {
                        // Toast.makeText(LoginOrRegister.this, "Email Format Wrong", Toast.LENGTH_SHORT).show();
                        error.setText("Email Format Wrong!");
                    }
                }
            }
        });
        registerPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    // clear error
                    error.setText("");
                }
                else {
                    // validate password format
                    String password = registerPassword.getText().toString();
                    if (!validatePassword(password)) {
                        error.setText("Password Format Wrong!");
                    }
                }
            }
        });
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
                    // reset password
                    loginPassword.setText("");
                }
                break;
            }
            case R.id.register_btn: {
                String username = registerUsername.getText().toString();
                String password = registerPassword.getText().toString();
                String repeatPassword = registerRepeatPassword.getText().toString();
                String email = registerEmail.getText().toString();
                if (register(username, password, repeatPassword, email)) {
                    Intent intent = new Intent(this, MainPage.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    // reset password
                    registerPassword.setText("");
                    registerRepeatPassword.setText("");
                }
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
        HttpSend.getInstance().login(username, password, new ResultCallbackListener<ImEntities.LoginResponse>() {
            @Override
            public void onSubscribe(Disposable d) { }

            @Override
            public void onNext(ImEntities.LoginResponse loginResponse) {
                ELog.e("Login Result: code =" + loginResponse.getCode() + "\t msg = " + loginResponse.getMessage());
                UserCenter.getInstance().setUser(loginResponse.getUser());
                Toast.makeText(LoginOrRegister.this, "Login Successful", Toast.LENGTH_SHORT).show();
                // give error msg if failed
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                ELog.e("error: " + e.getMessage());
                Toast.makeText(LoginOrRegister.this, "Network Error!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() { }
        });
        return true;
    }

    private boolean register(String username, String password, String repeatPassword, String email) {
        // validate
        if (username == null || password == null || password.length() > 20) {
            return false;
        }
        // call to server
        HttpSend.getInstance().register(username, password, email,new ResultCallbackListener<ImEntities.RegisternResponse>() {
            @Override
            public void onSubscribe(Disposable d) { }

            @Override
            public void onNext(ImEntities.RegisternResponse registerResponse) {
                ELog.e("Register Result: code =" + registerResponse.getCode() + "\t msg = " + registerResponse.getMessage());
                Toast.makeText(LoginOrRegister.this, "Login Successful", Toast.LENGTH_SHORT).show();
                // give error msg if failed
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                ELog.e("error: " + e.getMessage());
                Toast.makeText(LoginOrRegister.this, "Network Error!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() { }
        });
        return true;
    }

    private boolean validateEmail(String email) {
        Pattern p = Pattern.compile("/^[a-z]([a-z0-9]*[-_]?[a-z0-9]+)*@([a-z0-9]*[-_]?[a-z0-9]+)+[\\.][a-z]{2,3}([\\.][a-z]{2})?$/i");
        Matcher matcher = p.matcher(email);
        matcher.find();
        return matcher.matches();
    }

    private boolean validatePassword(String password) {
        Pattern p = Pattern.compile("^(?![0-9])(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$");
        Matcher matcher = p.matcher(password);
        matcher.find();
        return matcher.matches();
    }
}
