package com.example.smarthub;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MYViewModel extends ViewModel {
    private MutableLiveData<String> stringMutableLiveData;

    public void init(){

        stringMutableLiveData = new MutableLiveData<>();

    }

    public void sendMessage(String msg){

        stringMutableLiveData.setValue(msg);

    }
    public LiveData<String> getMessage(){

        return stringMutableLiveData;
    }

}
