package com.susanghan.android.ui.signup

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentSignUpResultBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpResultFragment :
    BaseFragment<FragmentSignUpResultBinding, SignUpResultViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_sign_up_result
    override val viewModel: SignUpResultViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.btnConfirm.setOnClickListener {
            navController?.popBackStack(R.id.signInFragment, false)
        }
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }

}