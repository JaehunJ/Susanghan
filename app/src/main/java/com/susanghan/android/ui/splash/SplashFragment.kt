package com.susanghan.android.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentSplashBinding
import com.susanghan.android.ui.dialog.ServiceErrorDialog
import org.koin.androidx.viewmodel.ext.android.*

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>() {
    override val layoutId: Int = R.layout.fragment_splash
    override val viewModel: SplashViewModel by viewModel()
    override val navArgs: NavArgs
        get() = TODO("Not yet implemented")

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }
}