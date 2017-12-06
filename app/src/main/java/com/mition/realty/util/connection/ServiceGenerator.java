package com.mition.realty.util.connection;

import com.mition.realty.util.constant.Constant;

import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

/**
 * Created by kksd0900 on 16. 9. 1..
 */
public class ServiceGenerator {
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static Retrofit retrofit;

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Constant.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        if (retrofit == null)
            retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }
}
