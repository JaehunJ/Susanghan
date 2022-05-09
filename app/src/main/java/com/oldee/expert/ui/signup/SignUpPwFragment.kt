package com.oldee.expert.ui.signup

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.oldee.expert.BuildConfig
import com.oldee.expert.R
import com.oldee.expert.base.BaseFragment
import com.oldee.expert.databinding.FragmentSignUpPwBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpPwFragment :
    BaseFragment<FragmentSignUpPwBinding, SignUpPwViewModel, SignUpPwFragmentArgs>() {
    override val layoutId: Int = R.layout.fragment_sign_up_pw
    override val viewModel: SignUpPwViewModel by viewModels()
    override val navArgs: SignUpPwFragmentArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.toolbar.tvTitle.text = ""

        binding.btnConfirm.setOnClickListener {
//            moveNext()
            if (viewModel.isValidate()) {
                if (viewModel.isChecked()) {
                    viewModel.requestSignUp()
                } else {
                    activityFuncFunction.showToast("약관에 동의해주세요.")
                }
            } else {
                activityFuncFunction.showToast("비밀번호를 확인해주세요.")
            }
        }

        binding.user = navArgs.data
        binding.viewModel = viewModel
        viewModel.prevData = navArgs.data

        binding.cb0.setOnClickListener {
            if (!binding.cb0.isChecked) {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(BuildConfig.TERM_SERVICE)
                )
                startActivity(intent)
            }
        }
        binding.cb1.setOnClickListener {
            if (!binding.cb1.isChecked) {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(BuildConfig.TERM_PRIVACY)
                )
                startActivity(intent)
            }
        }
    }

    override fun initDataBinding() {
        viewModel.isCbAll.observe(viewLifecycleOwner) {
            it?.let {
                viewModel.isCb0.postValue(it)
                viewModel.isCb1.postValue(it)
                viewModel.isCb2.postValue(it)
            }
        }

        viewModel.isSuccess.observe(viewLifecycleOwner) {
            it?.let {
                if (it.message == "success") {
                    moveNext()
//                    val action =
//                        SignUpPwFragmentDirections.actionSignUpPwFragmentToSignUpResultFragment()
//                    navController?.navigate(action)
                } else {
                    activityFuncFunction.showToast("잠시 후 다시 시도해 주세요.")
                }
            }
        }
    }

    override fun initAfterBinding() {

    }

    fun moveNext() {
        val action =
            SignUpPwFragmentDirections.actionSignUpPwFragmentToSignUpResultFragment()
        navController?.navigate(action)
    }
}