package com.susanghan.android.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.susanghan.android.R
import com.susanghan.android.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), CommonActivityImpl {
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

        val index = R.id.orderFragment

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val navController = navHostFragment!!.findNavController()
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when(destination.id){
                R.id.orderFragment, R.id.csFragment, R.id.moreFragment->{
                    showBottomNavi()
                }
                else->{
                    hideBottomNavi()
                }
            }
        }

        _binding.navBottom.setupWithNavController(navController)
        _binding.navBottom.itemIconTintList = null



//        //statusbar hide
        this.window?.apply {
//            this.statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        hideBottomNavi()
    }

    override fun showProgress() {
        TODO("Not yet implemented")
    }

    override fun hideProgress() {
        TODO("Not yet implemented")
    }

    override fun showBottomNavi() {
        _binding.llNav.visibility = View.VISIBLE
    }

    override fun hideBottomNavi() {
        _binding.llNav.visibility = View.GONE
    }
}