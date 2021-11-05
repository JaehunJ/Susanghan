package com.susanghan.android.ui.splash

import android.os.Bundle
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentSplashBinding
import com.susanghan.android.ui.dialog.ServiceErrorDialog
import dagger.hilt.android.AndroidEntryPoint
import org.koin.androidx.viewmodel.ext.android.*

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_splash
    override val viewModel: SplashViewModel by viewModel()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        activity?.let {
            val dialog = ServiceErrorDialog{
                val action = SplashFragmentDirections.actionSplashFragmentToSignInFragment()
                navController?.navigate(action)
            }

            dialog.show(it.supportFragmentManager, "error")
        }
//        activityFunction?.showBottomNavi()
//        val action = SplashFragmentDirections.actionGlobalCsFragment()
//        navController?.navigate(action)
    }
}