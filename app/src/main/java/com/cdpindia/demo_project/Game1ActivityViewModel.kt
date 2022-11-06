package com.cdpindia.demo_project

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class Game1ActivityViewModel:ViewModel() {

    var count = MutableLiveData<Boolean>(false)
    var click_count = MutableLiveData<Int>(0)
    var status = MutableLiveData<Boolean>(false)
    fun getCardData(view:View,position:Int){



    }
}