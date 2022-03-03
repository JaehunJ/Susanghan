package com.susanghan.android.ui.signin

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.data.TEST_ID
import com.susanghan.android.data.TEST_PW
import com.susanghan.android.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding, SignInViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_sign_in
    override val viewModel: SignInViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.btnLogin.setOnClickListener {
            viewModel.requestSignIn(TEST_ID, TEST_PW)
        }

        binding.tvFindId.setOnClickListener {
            val action = SignInFragmentDirections.actionSignInFragmentToFindIdFragment()
            navController?.navigate(action)
        }

        binding.tvFindPw.setOnClickListener {
            val action = SignInFragmentDirections.actionSignInFragmentToFindPwFragment()
            navController?.navigate(action)
        }

        binding.tvSignin1.setOnClickListener {
            val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            navController?.navigate(action)
        }
    }

    override fun initDataBinding() {
        viewModel.signInResponse.observe(this) {
            val action = SignInFragmentDirections.actionGlobalOrderFragment()
            navController?.navigate(action)
        }
    }

    override fun initAfterBinding() {

    }
}