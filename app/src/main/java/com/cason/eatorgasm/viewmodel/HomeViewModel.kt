package com.cason.eatorgasm.viewmodel

import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {

//    private val mHomeRepository = HomeRepositoryImpl.getInstance()

//    private var mCurrentStatus = HomeEnum.HomeFragmentStatus.eHome_Browser_Status
//    fun setFragmentStatus(status: HomeEnum.HomeFragmentStatus) {
//        mCurrentStatus = status
//    }
//
//    private var mCurrentBrowserPath = String()
//
//    private var mOpenItem = MutableLiveData<LiveDataEvent<HomeFileItem>>()
//    var onOpenEvent: LiveData<LiveDataEvent<HomeFileItem>> = mOpenItem
//
//    private var mHomeBottomSheetDialogMode = MutableLiveData<LiveDataEvent<Boolean>>()
//    var onHomeBottomSheetDialogEvent: LiveData<LiveDataEvent<Boolean>> = mHomeBottomSheetDialogMode
//
//    private var mOffCheckMode = MutableLiveData<LiveDataEvent<Boolean>>()
//    var onOffCheckModeEvent: LiveData<LiveDataEvent<Boolean>> = mOffCheckMode
//
//    private var mStartRecentTab = MutableLiveData<LiveDataEvent<Boolean>>()
//    var onStartRecentTabEvent: LiveData<LiveDataEvent<Boolean>> = mStartRecentTab
//
//    private var mStartBrowserTab = MutableLiveData<LiveDataEvent<Boolean>>()
//    var onStartBrowserTabEvent: LiveData<LiveDataEvent<Boolean>> = mStartBrowserTab
//
//    private var mStartFileTypeTab = MutableLiveData<LiveDataEvent<Boolean>>()
//    var onStartFileTypeTabEvent: LiveData<LiveDataEvent<Boolean>> = mStartFileTypeTab
//
//    private var mStartFavoriteTab = MutableLiveData<LiveDataEvent<Boolean>>()
//    var onStartFavoriteTabEvent: LiveData<LiveDataEvent<Boolean>> = mStartFavoriteTab
//
//    init {
//        mCurrentBrowserPath = HomeFileDefine.getBrowserRootPath
//    }
//
//    fun openDocument(item: HomeFileItem) {
//        mOpenItem.postValue(LiveDataEvent(item))
//        CoroutineScope(Dispatchers.Main).launch {
//            mHomeRepository.addRecentDB(item.strPath)
//        }
//    }
//
//    fun endCheckMode() {
//        mOffCheckMode.postValue(LiveDataEvent(true))
//    }
//
//    fun startRecentTab() {
//        mStartRecentTab.postValue(LiveDataEvent(true))
//    }
//
//    fun startBrowserTab() {
//        mStartBrowserTab.postValue(LiveDataEvent(true))
//    }
//
//    fun startFileTypeTab() {
//        mStartFileTypeTab.postValue(LiveDataEvent(true))
//    }
//
//    fun startFavoriteTab() {
//        mStartFavoriteTab.postValue(LiveDataEvent(true))
//    }
//
//    fun showBottomSheetDialog() {
//        mHomeBottomSheetDialogMode.postValue(LiveDataEvent(true))
//    }
//
//    fun hideBottomSheetDialog() {
//        mHomeBottomSheetDialogMode.postValue(LiveDataEvent(false))
//    }
//
//    fun getCheckedItemCount(): Int {
//        return mHomeRepository.getCheckedItemCount()
//    }
}