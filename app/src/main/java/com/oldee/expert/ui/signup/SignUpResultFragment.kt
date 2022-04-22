package com.oldee.expert.ui.signup

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.oldee.expert.R
import com.oldee.expert.base.BaseFragment
import com.oldee.expert.databinding.FragmentSignUpResultBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpResultFragment :
    BaseFragment<FragmentSignUpResultBinding, SignUpResultViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_sign_up_result
    override val viewModel: SignUpResultViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.btnConfirm.setOnClickListener {
            navController?.navigate(SignUpResultFragmentDirections.actionSignUpResultFragmentToSignInFragment())
        }
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }
}