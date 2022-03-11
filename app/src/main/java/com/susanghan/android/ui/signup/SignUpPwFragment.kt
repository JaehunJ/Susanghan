package com.susanghan.android.ui.signup

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentSignUpPwBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpPwFragment : BaseFragment<FragmentSignUpPwBinding, SignUpPwViewModel, SignUpPwFragmentArgs>() {
    override val layoutId: Int = R.layout.fragment_sign_up_pw
    override val viewModel: SignUpPwViewModel by viewModels()
    override val navArgs: SignUpPwFragmentArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.toolbar.tvTitle.text = ""

        binding.btnConfirm.setOnClickListener {
            val action = SignUpPwFragmentDirections.actionSignUpPwFragmentToSignUpResultFragment()
            navController?.navigate(action)
        }

        binding.user = navArgs.data
        binding.viewModel = viewModel
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }

}