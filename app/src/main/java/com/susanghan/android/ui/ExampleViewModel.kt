package com.susanghan.android.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ExampleViewModel:ViewModel() {
    val TAG = this.javaClass.simpleName

    private val _exampleLiveData : MutableLiveData<ExampleData> = MutableLiveData()

//    var printService:PrintService by inject()
    val exampleData:LiveData<ExampleData>
    get() = _exampleLiveData

    fun requestData(){
        val exampleData = ExampleData().apply {
            value = 10
        }

        _exampleLiveData.value = exampleData
    }

    override fun onCleared() {
        super.onCleared()
        Log.v(TAG, "onCleard")
    }

    fun printHello(){
//        printService.printHello()
    }
}

class ExampleData{
    var value:Int = 0
}