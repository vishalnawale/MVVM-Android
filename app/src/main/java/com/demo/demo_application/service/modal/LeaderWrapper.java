package com.demo.demo_application.service.modal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LeaderWrapper  {
    @SerializedName("data")
    private List<Leaders> mData;
    @SerializedName("error")
    private Boolean mError;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private String mStatus;


    public List<Leaders> getmData() {
        return mData;
    }

    public void setmData(List<Leaders> mData) {
        this.mData = mData;
    }

    public Boolean getmError() {
        return mError;
    }

    public void setmError(Boolean mError) {
        this.mError = mError;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public String getmStatus() {
        return mStatus;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }
}
