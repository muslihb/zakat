package com.example.zakat.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroServer {
//    private static final String baseURL = "http://192.168.100.2/zakat/";
    private static final String baseURL = "https://deksnime.xyz/zakat/";
    private static Retrofit retro = null;

    public static Retrofit konekRetrofit(){
        if(retro == null){
            retro = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retro;
    }
}
