package com.susanghan.android.ui.splash

import android.Manifest
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.custom.checkPermission
import com.susanghan.android.databinding.FragmentSplashBinding
import com.susanghan.android.ui.dialog.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_splash
    override val viewModel: SplashViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    private val permissions =
        arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private val requestPermissionResult =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (it.all { per -> per.value == true }) {
                showUpdateDialog()
            }
        }


    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        activity?.let {
            val dialog = ServiceErrorDialog {
//                showServiceCheckDialog()
                showPermissionInfoDialog()
//                val action = SplashFragmentDirections.actionSplashFragmentToSignInFragment()
//                navController?.navigate(action)
            }

            dialog.isCancelable = false
            dialog.show(it.supportFragmentManager, "error")
        }
//        activityFunction?.showBottomNavi()
//        val action = SplashFragmentDirections.actionGlobalCsFragment()
//        navController?.navigate(action)
    }

    fun showServiceCheckDialog() {
        val dialog = ServiceCheckDialog {
            showPermissionInfoDialog()
        }

        dialog.isCancelable = false
        activity?.let {
            dialog.show(it.supportFragmentManager, "check")
        }
    }

    fun showPermissionInfoDialog() {
        val check = checkPermission(requireContext(), *permissions)

        if (!check) {
            val dialog = PermissionDialog {
                requestPermissionResult.launch(permissions)
            }
            dialog.isCancelable = false

            activity?.let {
                dialog.show(it.supportFragmentManager, "permission")
            }
        } else {
//            showUpdateDialog()
            val action = SplashFragmentDirections.actionSplashFragmentToSignInFragment()
            navController?.navigate(action)
        }
    }

    fun showForceUpdateDialog() {
        val dialog = ForceUpdateDialog {
            val action = SplashFragmentDirections.actionSplashFragmentToSignInFragment()
            navController?.navigate(action)
        }
        dialog.isCancelable = false

        activity?.let {
            dialog.show(it.supportFragmentManager, "force_update")
        }
    }

    fun showUpdateDialog() {
        val dialog = UpdateDialog({
            showForceUpdateDialog()
        }) {
            showForceUpdateDialog()
        }
        dialog.isCancelable = false

        activity?.let {
            dialog.show(it.supportFragmentManager, "update")
        }
    }
}