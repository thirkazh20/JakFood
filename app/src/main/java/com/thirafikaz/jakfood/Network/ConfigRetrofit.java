package com.thirafikaz.jakfood.Network;

import com.thirafikaz.jakfood.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfigRetrofit {
    private static Retrofit setInit() {
        return new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .build();
    }

    public static ApiService getInstance() {
        return setInit().create(ApiService.class);
    }
}
