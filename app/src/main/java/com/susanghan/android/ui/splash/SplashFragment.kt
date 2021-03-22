package com.susanghan.android.ui.splash

import android.os.Bundle
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentSplashBinding
import org.koin.androidx.viewmodel.ext.android.*

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_splash
    override val viewModel: SplashViewModel by viewModel()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
//        val action = SplashFr
//        navController.navigate()
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }
}