package vn.iostar.ltdidong_baitap06;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseClient { // Không xài
    private static final HttpLoggingInterceptor sLogging =
            new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static final OkHttpClient.Builder sHttpClient = new OkHttpClient.Builder();

    public static <S> S createService(Class<S> serviceClass, String baseUrl) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create());

        // Thêm interceptor nếu chưa được thêm
        if (!sHttpClient.interceptors().contains(sLogging)) {
            sHttpClient.addInterceptor(sLogging);
        }

        builder.client(sHttpClient.build());
        Retrofit retrofit = builder.build();

        return retrofit.create(serviceClass);
    }
}
