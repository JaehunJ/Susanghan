package com.susanghan.android.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.susanghan.android.R
import com.susanghan.android.databinding.ActivityMainBinding
import com.susanghan.android.ui.dialog.ServiceErrorDialog

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

        val dialog = ServiceErrorDialog()
        dialog.show(supportFragmentManager, "service")

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val navController = navHostFragment!!.findNavController()
        _binding.navBottom.setupWithNavController(navController)
    }
}