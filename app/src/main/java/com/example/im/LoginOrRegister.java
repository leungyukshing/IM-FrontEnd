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

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                error.setText("");
                if (s == "Login") {
                    // clear password on Register
                    registerPassword.setText("");
                    registerRepeatPassword.setText("");
                }
                else {
                    // clear password on Login
                    loginPassword.setText("");
                }
            }
        });

        // Register Button Listener
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);

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
        ELog.e("onClick");
        switch (view.getId()) {
            case R.id.login_btn: {
                String username = loginUsername.getText().toString();
                String password = loginPassword.getText().toString();
                login(username, password);
                break;
            }
            case R.id.register_btn: {
                String username = registerUsername.getText().toString();
                String password = registerPassword.getText().toString();
                String repeatPassword = registerRepeatPassword.getText().toString();
                String email = registerEmail.getText().toString();
                register(username, password, repeatPassword, email);
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
                // succeed
                if (loginResponse.getCode().equals("200") && loginResponse.getMessage().equals("Login Success")) {
                    // save user info
                    UserCenter.getInstance().setUser(loginResponse.getUser());
                    // Jump to MainPage
                    Intent intent = new Intent(getApplicationContext(), MainPage.class);
                    startActivity(intent);
                    finish();
                }
                // fail
                else {
                    Toast.makeText(LoginOrRegister.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    // reset password
                    loginPassword.setText("");
                }
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
                // success
                if (registerResponse.getCode().equals("200") && registerResponse.getMessage().equals("Register Success")) {
                    Intent intent = new Intent(getApplicationContext(), LoginOrRegister.class);
                    startActivity(intent);
                    finish();
                }
                // fail
                else {
                    // reset password
                    registerPassword.setText("");
                    registerRepeatPassword.setText("");
                    Toast.makeText(LoginOrRegister.this, "Register Failed", Toast.LENGTH_SHORT).show();
                }
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

    protected boolean validateEmail(String email) {
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher matcher = p.matcher(email);
        matcher.find();
        return matcher.matches();
    }

    protected boolean validatePassword(String password) {
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher matcher = p.matcher(password);
        matcher.find();
        return matcher.matches();
    }
}
