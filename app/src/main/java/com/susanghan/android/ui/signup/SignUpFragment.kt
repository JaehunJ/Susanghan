package com.susanghan.android.ui.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentSignUpBinding



class SignUpFragment : BaseFragment<FragmentSignUpBinding, SignUpViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_sign_up
    override val viewModel: SignUpViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.btnConfirm.setOnClickListener {
            val action = SignUpFragmentDirections.actionSignUpFragmentToSignUpPwFragment()
            navController?.navigate(action)
        }
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }
}