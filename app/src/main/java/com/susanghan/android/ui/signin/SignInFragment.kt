package com.susanghan.android.ui.signin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.susanghan.android.NavSigninDirections
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentSignInBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInFragment : BaseFragment<FragmentSignInBinding, SignInViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_sign_in
    override val viewModel: SignInViewModel by viewModel()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.btnLogin.setOnClickListener {
//            val action = SignInFragmentDirections.()
            val action = NavSigninDirections.actionGlobalOrderFragment()
            navController?.navigate(action)
            activityFunction.showBottomNavi()
        }
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }

}