package com.susanghan.android.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.susanghan.android.R
import com.susanghan.android.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), CommonActivityFuncImpl {
    private lateinit var _binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
//        val splash = installSplashScreen()
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = _binding.root
        setContentView(view)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val navController = navHostFragment!!.findNavController()

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.orderFragment, R.id.csFragment, R.id.designFragment, R.id.moreFragment -> {
                    showBottomNavi()
                }
                else -> {
                    hideBottomNavi()
                }
            }
        }

        _binding.navBottom.setupWithNavController(navController)
        _binding.navBottom.itemIconTintList = null

        this.window?.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        hideBottomNavi()
    }

    override fun showProgress() {
        _binding.rlProgress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        _binding.rlProgress.visibility = View.GONE
    }

    override fun showBottomNavi() {
        _binding.llNav.visibility = View.VISIBLE
    }

    override fun hideBottomNavi() {
        _binding.llNav.visibility = View.GONE
    }

    override fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}