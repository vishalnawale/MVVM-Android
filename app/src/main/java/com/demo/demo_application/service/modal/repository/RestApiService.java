package com.demo.demo_application.service.modal.repository;

import com.demo.demo_application.service.modal.LeaderWrapper;


import retrofit2.Call;
import retrofit2.http.GET;

public interface RestApiService {

    @GET("leaders")
    Call<LeaderWrapper> getLeadersList();
}
