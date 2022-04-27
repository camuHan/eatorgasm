package com.cason.eatorgasm.component;

import androidx.lifecycle.LiveData;

import com.cason.eatorgasm.model.entity.UserInfoModel;

public interface EatHomeActivityActionListener {
    LiveData<UserInfoModel> getUserInfoLiveData();
}
