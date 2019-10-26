package com.example.im;

import android.content.Context;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import io.reactivex.Observable;

public class HttpSend {
    private static HttpSend httpSend;
    private NetworkService networkService;

    private HttpSend() { }

    public static HttpSend getInstance() {
        if (httpSend == null) {
            synchronized (HttpSend.class) {
                if (httpSend == null) {
                    httpSend = new HttpSend();
                }
            }
        }
        return httpSend;
    }

    public void initContext(Context context) {
        HttpConfiger.getInstance().initContext(context.getApplicationContext());
        networkService = HttpConfiger.getInstance().getRetrofit().create(NetworkService.class);
    }

    public void login(String email, String password, ResultCallbackListener<ImEntities.LoginResponse> subscriber) {
        // construct request login
        ImEntities.LoginRequest loginRequest = ImEntities.LoginRequest.newBuilder().setEmail(email).setPassword(password).build();
        RequestBody params = RequestBody.create(MediaType.parse("application/octet-stream"), loginRequest.toByteArray());
        Observable<ImEntities.LoginResponse> loginResponseObservable = networkService.login(params);
        HttpConfiger.getInstance().toSubscribe(loginResponseObservable).subscribe(subscriber);
    }

    public void register(String username, String password, String email, ResultCallbackListener<ImEntities.RegisternResponse> subscriber) {
        // construct request for register
        ImEntities.RegisterRequest registerRequest = ImEntities.RegisterRequest.newBuilder().setUsername(username).setPassword(password).setEmail(email).build();
        RequestBody params = RequestBody.create(MediaType.parse("application/octet-stream"), registerRequest.toByteArray());
        Observable<ImEntities.RegisternResponse> registernResponseObservable = networkService.register(params);
        HttpConfiger.getInstance().toSubscribe(registernResponseObservable).subscribe(subscriber);
    }
}
