package com.demo.demo_application.service.modal.repository;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import android.widget.Toast;

import com.demo.demo_application.service.modal.LeaderWrapper;
import com.demo.demo_application.service.modal.Leaders;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private ArrayList<Leaders> leaders=new ArrayList<>();
    private MutableLiveData<List<Leaders>> leadersMutableLiveData=new MutableLiveData<>();
    Application application;

    public Repository(Application application) {
        this.application = application;
    }

    public  MutableLiveData<List<Leaders>> getMutableLiveData(){
        RestApiService restApiService=RetrofitInstance.getApiService();
        Log.d("RetrofitURL",RetrofitInstance.getApiService().toString());


        Call<LeaderWrapper> call=restApiService.getLeadersList();
        Log.d("Data URL",call.request().url().toString());

        call.enqueue(new Callback<LeaderWrapper>() {
            @Override
            public void onResponse(Call<LeaderWrapper> call, Response<LeaderWrapper> response) {
                LeaderWrapper leaderWrapper=response.body();

                if(leaderWrapper !=null && leaderWrapper.getmData()!=null){
                    leaders=(ArrayList<Leaders>) leaderWrapper.getmData();
                    leadersMutableLiveData.setValue(leaders);
                }
            }

            @Override
            public void onFailure(Call<LeaderWrapper> call, Throwable t) {
             Log.d("Data Failed",t.getMessage());
                Log.d("Data URL",call.request().url().toString());

            }
        });
        return  leadersMutableLiveData;
    }
}
