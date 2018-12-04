package com.chuongvd.app.example.data.source.remote.service;

import android.text.TextUtils;
import com.chuongvd.app.example.BuildConfig;
import com.chuongvd.app.example.data.source.repository.UserRepository;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestHelper {
    public static final int CONNECTION_TIMEOUT = 30;
    public static final String IMAGE_TYPE = "image/*";
    private static final int MAXIMUM_REQUEST_RETRY = 5;
    public static AppApi instance;

    public static AppApi getRequest() {
        return instance == null ? instance = makeRequest(BuildConfig.END_POINT, true) : instance;
    }

    public static AppApi newInstance(boolean isFormatted) {
        return makeRequest(BuildConfig.END_POINT, isFormatted);
    }

    private static AppApi makeRequest(String baseUrl, boolean isFormatted) {
        HttpLoggingInterceptor logging = getHttpLoggingInterceptor();
        OkHttpClient.Builder httpClient = getUnsafeOkHttpClient();
        httpClient.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        httpClient.readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);
        httpClient.addInterceptor(requestWithHeader());
        //        httpClient.authenticator(new TokenAuthenticator());
        GsonBuilder gsonBuilder = new GsonBuilder();
        if (!isFormatted) gsonBuilder.setLenient();
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(
                GsonConverterFactory.create(gsonBuilder.create()))
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .baseUrl(baseUrl)
                .client(httpClient.build())
                .build();
        return retrofit.create(AppApi.class);
    }

    private static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.NONE);
        return interceptor;
    }

    private static OkHttpClient.Builder getUnsafeOkHttpClient() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain,
                                String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain,
                                String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[0];
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            httpClientBuilder.sslSocketFactory(sslSocketFactory)
                    .hostnameVerifier(
                            org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
                    .build();
            return httpClientBuilder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Interceptor requestWithHeader() {
        return chain -> {
            Request original = chain.request();
            Request.Builder builder = addAuthenticateHeader(original.newBuilder(), original);
            Response response = chain.proceed(builder.build());
            if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                builder = addAuthenticateHeader(builder, original);
                original = builder.build();
                response = chain.proceed(original);
            }
            return response;
        };
    }

    private static Request.Builder addAuthenticateHeader(Request.Builder builder,
            Request original) {
        String accessToken = UserRepository.getInstance().getAccessToken();
        builder.addHeader("Cache-Control", "no-cache")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .method(original.method(), original.body());
        if (BuildConfig.DEBUG) {
            System.out.println(String.format("token: %s", accessToken));
        }
        if (!TextUtils.isEmpty(accessToken)) {
            builder.header("Authorization", "Token " + accessToken);
        }

        return builder;
    }

    public static MultipartBody.Part buildFileMultipartFromUri(File file, String name,
            String fileType) {
        RequestBody fileBody;
        MultipartBody.Part part = null;
        if (file != null) {
            fileBody = RequestBody.create(MediaType.parse(fileType), file);
            part = MultipartBody.Part.createFormData(name, file.getName(), fileBody);
        }
        return part;
    }
}
