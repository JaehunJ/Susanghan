package com.oldee.expert.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.oldee.expert.R
import com.oldee.expert.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), CommonActivityFuncImpl {
    private lateinit var _binding: ActivityMainBinding

    private var backTime = 0L
    private var useableTwiceBack = false

    var toast: Toast? = null
    var prev: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val lp = window.attributes as WindowManager.LayoutParams
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT
//        lp.height = WindowManager.LayoutParams.MATCH_PARENT
//        window.attributes = lp

        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = _binding.root
        setContentView(view)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val navController = navHostFragment!!.findNavController()

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            prev = destination.id
            when (destination.id) {
                R.id.signInFragment -> {
                    useableTwiceBack = true
                    hideBottomNavi()
                }
                R.id.orderFragment -> {
                    useableTwiceBack = true
                    showBottomNavi()
                }
                R.id.csFragment -> {
                    useableTwiceBack = false
                    showBottomNavi()
                }
                R.id.designFragment -> {
                    useableTwiceBack = false
                    showBottomNavi()
                }
                R.id.moreFragment -> {
                    useableTwiceBack = false
                    showBottomNavi()
                }
                else -> {
                    useableTwiceBack = false
                    hideBottomNavi()
                }
            }
        }

        _binding.navBottom.setupWithNavController(navController)
        _binding.navBottom.itemIconTintList = null

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

    override fun showSnackBar(msg: String) {
        Snackbar.make(_binding.root.rootView, msg, Snackbar.LENGTH_SHORT).show()
    }

    override fun showSnackBarWithButton(msg: String, btnText: String, onClick: () -> Unit) {
        Snackbar.make(_binding.root.rootView, msg, Snackbar.LENGTH_SHORT).apply {
            setAction(btnText) {
                onClick()
            }
        }.show()
    }

    override fun showToast(msg: String) {
        toast?.cancel()
        toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
        toast?.show()
    }

    override fun onBackPressed() {
        if (useableTwiceBack) {
            if (System.currentTimeMillis() - backTime > 1000) {
                //show toast
                backTime = System.currentTimeMillis()
                showToast("한번 더 누르시면 앱을 종료합니다.")
            } else {
                this.finish()
            }
        } else {
            super.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()

        when (prev) {
            R.id.signInFragment -> {
                useableTwiceBack = true
                hideBottomNavi()
            }
            R.id.orderFragment -> {
                useableTwiceBack = true
                showBottomNavi()
            }
            R.id.csFragment -> {
                useableTwiceBack = false
                showBottomNavi()
            }
            R.id.designFragment -> {
                useableTwiceBack = false
                showBottomNavi()
            }
            R.id.moreFragment -> {
                useableTwiceBack = false
                showBottomNavi()
            }
            else -> {
                useableTwiceBack = false
                hideBottomNavi()
            }
        }
    }
}