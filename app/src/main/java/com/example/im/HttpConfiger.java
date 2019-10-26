package com.example.im;

import android.content.Context;

import com.google.protobuf.ExtensionRegistryLite;
import com.hdl.elog.ELog; // https://github.com/huangdali/ELog
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.protobuf.ProtoConverterFactory;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HttpConfiger {
    /* connection timeout */
    private static final int DEFAULT_CONN_TIMEOUT = 10;

    /* read timeout */
    private static final int DEFAULT_READ_TIMEOUT = 60;

    /* write timeout */
    private static final int DEFAULT_WRITE_TIMEOUT = 5 * 60;

    /* server url */
    private static final String serverUrl = "http://";

    /* local url */
    private static final String localUrl = "http://localhost:8080";

    private Context context;

    private Retrofit retrofit;

    private Retrofit.Builder retrofitBuilder;

    private static HttpConfiger httpConfiger;

    private HttpConfiger() {}

    public static HttpConfiger getInstance() {
        if (httpConfiger == null) {
            synchronized (HttpConfiger.class) {
                if (httpConfiger == null) {
                    httpConfiger = new HttpConfiger();
                }
            }
        }
        return httpConfiger;
    }

    /* register Okhttp and Retrofit*/
    public void initContext(Context context) {
        this.context = context.getApplicationContext();
        initHttp();
    }

    private void initHttp() {
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_CONN_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(httpInterceptor)
                .retryOnConnectionFailure(true)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        // trust all host
                        return true;
                    }
                });

        ExtensionRegistryLite registry = ExtensionRegistryLite.newInstance();
        // RxJava2CallAdapter: https://www.ctolib.com/retrofit2-rxjava2-adapter.html
        retrofitBuilder = new Retrofit.Builder()
                .client(httpBuilder.build())
                .baseUrl(localUrl)
                .addConverterFactory(ProtoConverterFactory.createWithRegistry(registry))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        retrofit = retrofitBuilder.build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public <T>Observable<T> toSubscribe(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Interceptor httpInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = addPublicParameter(chain.request());
            Response response = chain.proceed(request);
            return response;
        }
    };

    private Request addPublicParameter(Request request) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        HttpUrl.Builder builder = request
                .url()
                .newBuilder()
                .addQueryParameter("timestamp", timestamp);
        ELog.e("url = " + builder.build());
        Request newRequest = request
                .newBuilder()
                .method(request.method(), request.body())
                .url(builder.build())
                .build();
        return newRequest;

    }
}
