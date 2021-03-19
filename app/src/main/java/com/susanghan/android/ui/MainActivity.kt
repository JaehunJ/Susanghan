package com.susanghan.android.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.susanghan.android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val TAG = this.javaClass.simpleName

    private val _exampleLiveData : MutableLiveData<ExampleData> = MutableLiveData()

    val exampleData:LiveData<ExampleData>
    get() = _exampleLiveData

//    private lateinit var _binding:ActivityMain
    private lateinit var _binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = _binding.root
        setContentView(view)
    }
}