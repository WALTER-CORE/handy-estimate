package com.walter.handyestimate.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.walter.handyestimate.data.model.TestModel;
import com.walter.handyestimate.R;

public class LoginViewModel extends ViewModel {
    private TestModel testSub;

    public LoginViewModel(String companyName) {
        testSub = new TestModel(companyName);
    }

    public void login(String companyName) {
        // can be launched in a separate asynchronous job

    }
}