package com.oldee.expert.ui.signin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.oldee.expert.BuildConfig
import com.oldee.expert.R
import com.oldee.expert.base.BaseFragment
import com.oldee.expert.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding, SignInViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_sign_in
    override val viewModel: SignInViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.viewModel = viewModel

        binding.btnLogin.setOnClickListener {
            val id = viewModel.id.value
            val pw = viewModel.pw.value
            if (!id.isNullOrEmpty() && !pw.isNullOrEmpty()) {
                viewModel.requestSignIn(viewModel.id.value ?: "", viewModel.pw.value ?: "")
            } else {
                activityFuncFunction.showToast("아이디와 비밀번호를 입력해주세요.")
            }
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

        binding.tvSignin.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.STORE_URL))
            startActivity(intent)
        }
    }

    override fun initDataBinding() {
        viewModel.signInResponse.observe(viewLifecycleOwner) {
            if (it == null) {
                activityFuncFunction.showToast("로그인 오류, 아이디와 비밀번호를 확인 해주세요")
                return@observe
            }
            if (it.message == "success") {
                val action = SignInFragmentDirections.actionSignInFragmentToOrderFragment()
                navController?.navigate(action)
            }
        }
//        viewModel.hasError().observe(viewLifecycleOwner){
//            it?.let{
//                if(it){
//                    navController?.navigate(R.id.action_global_networkErrorFragment)
//                }
//            }
//        }
    }

    override fun initAfterBinding() {
//        if (viewModel.checkAutoLogin()) {
//            val list = viewModel.loadAutoLogin()
//            viewModel.requestSignIn(list[0], list[1])
//        }
    }
//
//    override fun onViewCreated() {
//        super.onViewCreated()
//
//        viewModel.callErrorFragment()
//    }
}