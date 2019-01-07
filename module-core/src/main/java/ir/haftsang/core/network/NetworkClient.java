package ir.haftsang.core.network;

import java.util.concurrent.TimeUnit;

import ir.haftsang.core.util.GsonUtils;
import ir.haftsang.core.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by p.kokabi on 3/10/2018.
 */

public class NetworkClient {

    private static final long CONNECT_TIMEOUT = 30;
    private static final long READ_TIMEOUT = 30;
    private static final long WRITE_TIMEOUT = 30;

    private static Retrofit retrofit;

    public synchronized Retrofit getRetrofit(OkHttpClient.Builder httpClient) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(new EnvelopeConverter())
                .addConverterFactory(GsonConverterFactory.create(GsonUtils.getGson()))
                .client(httpClient.build())
                .build();
    }

    public void addTimeout(OkHttpClient.Builder httpClient, int connectTimeout, int readTimeout, int writeTimeout) {
        httpClient.connectTimeout(connectTimeout, TimeUnit.SECONDS);
        httpClient.readTimeout(readTimeout, TimeUnit.SECONDS);
        httpClient.writeTimeout(writeTimeout, TimeUnit.SECONDS);
    }

    public void addTimeoutDefault(OkHttpClient.Builder httpClient) {
        httpClient.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        httpClient.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        httpClient.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
    }

    public void addHeaderDefault(OkHttpClient.Builder httpClient) {
        httpClient.addInterceptor(chain -> {
            Request.Builder requestBuilder = chain.request().newBuilder();
            requestBuilder.addHeader("platform", "android");
            requestBuilder.addHeader("version", String.valueOf(BuildConfig.VERSION_CODE));
            return chain.proceed(requestBuilder.build());
        });
    }

    public void addHeader(OkHttpClient.Builder httpClient, Interceptor headerInterceptor) {
        httpClient.addInterceptor(headerInterceptor);
    }

    public void addLoge(OkHttpClient.Builder httpClient) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
                .setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        httpClient.addInterceptor(logging);
    }

}