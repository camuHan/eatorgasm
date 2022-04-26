package com.cason.eatorgasm.component;

import androidx.lifecycle.LiveData;

import com.cason.eatorgasm.model.entity.EatUserProfileItem;

public interface EatHomeActivityActionListener {
    LiveData<EatUserProfileItem> getUserInfoLiveData();
}
