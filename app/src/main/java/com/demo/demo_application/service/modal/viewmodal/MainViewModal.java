package com.demo.demo_application.service.modal.viewmodal;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.demo.demo_application.service.modal.Leaders;
import com.demo.demo_application.service.modal.repository.Repository;

import java.util.List;

public class MainViewModal extends AndroidViewModel  {
    private Repository repository;
    public MainViewModal(@NonNull Application application) {
        super(application);
        repository=new Repository(application);

    }
    public LiveData<List<Leaders>> getAllLeadersList(){
        return repository.getMutableLiveData();
    }
}
