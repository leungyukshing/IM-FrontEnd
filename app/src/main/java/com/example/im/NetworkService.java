package com.example.im;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import io.reactivex.Observable;

public interface NetworkService {
    @POST("login.action")
    Observable<ImEntities.LoginResponse> login(@Body RequestBody body);

    @POST("register.action")
    Observable<ImEntities.RegisternResponse> register(@Body RequestBody body);

    @GET("getChatList.action")
    Observable<ImEntities.GetChatListResponse> getChatListByUserID(@Body RequestBody body);

    @GET("getContactsLayout.action")
    Observable<ImEntities.GetContactListResponse> getContactListByUserID(@Body RequestBody body);
}
