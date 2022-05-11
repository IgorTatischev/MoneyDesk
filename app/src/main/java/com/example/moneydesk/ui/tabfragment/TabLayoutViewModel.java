package com.example.moneydesk.ui.tabfragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TabLayoutViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public TabLayoutViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}