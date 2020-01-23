package com.demo.demo_application.service.modal.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.demo.demo_application.R;
import com.demo.demo_application.service.modal.Leaders;
import com.demo.demo_application.service.modal.viewmodal.MainViewModal;

import java.util.List;

public class LeadersActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    SwipeRefreshLayout swipeRefresh;
    private MainViewModal mainViewModel;

    LeaderAdapter leaderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaders);
        initializationViews();
        mainViewModel=ViewModelProviders.of(this).get(MainViewModal.class);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMoreLeaders();
            }
        });

    }

    private void initializationViews() {
        swipeRefresh = findViewById(R.id.swiperefresh);
        mRecyclerView = findViewById(R.id.blogRecyclerView);
    }

    public void getMoreLeaders(){
        swipeRefresh.setRefreshing(true);
        mainViewModel.getAllLeadersList().observe(this, new Observer<List<Leaders>>() {
            @Override
            public void onChanged(@Nullable List<Leaders> blogList) {
                swipeRefresh.setRefreshing(false);
                prepareRecyclerView(blogList);
            }
        });
    }

    private void prepareRecyclerView(List<Leaders> blogList) {

        leaderAdapter = new LeaderAdapter(blogList);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));

        }
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(leaderAdapter);
        leaderAdapter.notifyDataSetChanged();

    }
}
