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

    public void register(String username, String password, String email, ResultCallbackListener<ImEntities.RegisterResponse> subscriber) {
        // construct request for register
        ImEntities.RegisterRequest registerRequest = ImEntities.RegisterRequest.newBuilder().setUsername(username).setPassword(password).setEmail(email).build();
        RequestBody params = RequestBody.create(MediaType.parse("application/octet-stream"), registerRequest.toByteArray());
        Observable<ImEntities.RegisterResponse> registernResponseObservable = networkService.register(params);
        HttpConfiger.getInstance().toSubscribe(registernResponseObservable).subscribe(subscriber);
    }

    public void getChatListByUserID(String userID, ResultCallbackListener<ImEntities.GetChatListResponse> subscriber) {
        // construct request for getChatListByUserID
        ImEntities.GetChatListRequest getChatListRequest = ImEntities.GetChatListRequest.newBuilder().setUserid(userID).build();
        RequestBody params = RequestBody.create(MediaType.parse("application/octet-stream"), getChatListRequest.toByteArray());
        Observable<ImEntities.GetChatListResponse> getChatListResponseObservable = networkService.getChatListByUserID(params);
        HttpConfiger.getInstance().toSubscribe(getChatListResponseObservable).subscribe(subscriber);
    }

    public void getContactListByUserID(String userID, ResultCallbackListener<ImEntities.GetContactListResponse> subscriber) {
        // construct request for getContactListByUserID
        ImEntities.GetContactListRequest getContactListRequest = ImEntities.GetContactListRequest.newBuilder().setUserid(userID).build();
        RequestBody params = RequestBody.create(MediaType.parse("application/octet-stream"), getContactListRequest.toByteArray());
        Observable<ImEntities.GetContactListResponse> getContactListResponseObservable = networkService.getContactListByUserID(params);
        HttpConfiger.getInstance().toSubscribe(getContactListResponseObservable).subscribe(subscriber);
    }

}
