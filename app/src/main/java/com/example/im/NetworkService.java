package com.example.im;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import io.reactivex.Observable;

public interface NetworkService {
    @POST("login")
    Observable<ImEntities.LoginResponse> login(@Body RequestBody body);

    @POST("register")
    Observable<ImEntities.RegisterResponse> register(@Body RequestBody body);

    @POST("getChatList")
    Observable<ImEntities.GetChatListResponse> getChatListByUserID(@Body RequestBody body);

    @POST("getContactList")
    Observable<ImEntities.GetContactListResponse> getContactListByUserID(@Body RequestBody body);
}
