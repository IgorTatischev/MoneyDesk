package com.example.moneydesk.ui.checkfragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CheckViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CheckViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}