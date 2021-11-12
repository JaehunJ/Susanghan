package com.susanghan.android.ui.splash

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentSplashBinding
import com.susanghan.android.ui.dialog.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_splash
    override val viewModel: SplashViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        activity?.let {
            val dialog = ServiceErrorDialog {
                showServiceCheckDialog()
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
        val dialog = PermissionDialog {
            showUpdateDialog()
        }
        dialog.isCancelable = false
//        dialog.setConfirmEvent()

        activity?.let {
            dialog.show(it.supportFragmentManager, "permission")
        }
    }

    fun showForceUpdateDialog() {
        val dialog = ForceUpdateDialog {
            val action = SplashFragmentDirections.actionSplashFragmentToSignInFragment()
            navController?.navigate(action)
        }
        dialog.isCancelable = false

        activity?.let{
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

        activity?.let{
            dialog.show(it.supportFragmentManager, "update")
        }
    }
}