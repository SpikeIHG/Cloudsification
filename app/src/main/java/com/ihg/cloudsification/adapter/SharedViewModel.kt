package com.ihg.cloudsification.adapter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    // 用 MutableLiveData 来持有动态变化的数据
    val cloudCountLiveData = MutableLiveData<String>()

    val geneCountLiveData = MutableLiveData<String>()

    val badgeCountLiveData = MutableLiveData<String>()

    val CloudMostRecordedLiveData = MutableLiveData<String>()

// time 相关的
    val TimeDiffLiveDate = MutableLiveData<String>()

    fun setCloudCount(count: String) {
        cloudCountLiveData.value = count
    }

    fun setGeneCount(gcount: String){
        geneCountLiveData.value = gcount
    }

    fun setBadgeCount(bcount: String){
        badgeCountLiveData.value = bcount
    }

    fun setCloudRecorded(most: String){
        CloudMostRecordedLiveData.value = most
    }

    fun setTimeDiff(daydiff : String){
        TimeDiffLiveDate.value = daydiff
    }

}
