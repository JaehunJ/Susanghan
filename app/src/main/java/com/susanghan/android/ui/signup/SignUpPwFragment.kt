package com.susanghan.android.ui.signup

import android.os.Bundle
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentSignUpPwBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpPwFragment : BaseFragment<FragmentSignUpPwBinding, SignUpPwViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_sign_up_pw
    override val viewModel: SignUpPwViewModel by viewModel()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.toolbar.tvTitle.text = ""

        binding.btnConfirm.setOnClickListener {
            val action = SignUpPwFragmentDirections.actionSignUpPwFragmentToSignUpResultFragment()
            navController?.navigate(action)
        }

    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }

}