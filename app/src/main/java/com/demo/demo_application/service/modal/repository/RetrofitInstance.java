package com.demo.demo_application.service.modal.repository;



import com.demo.demo_application.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    public static final String BASE_URL ="http://192.168.0.101:3000/";

    private static Retrofit retrofit=null;
    public  static RestApiService getApiService(){
        if(retrofit ==null){
            retrofit =new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(RestApiService.class);

    }
}
