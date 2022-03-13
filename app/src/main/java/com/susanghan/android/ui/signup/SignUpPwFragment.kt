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
            if(viewModel.isValidate()){
                if(viewModel.isChecked()){
                    viewModel.requestSignUp()
                }else{
                    activityFuncFunction.showToast("약관에 동의해주세요.")
                }
            }else{
                activityFuncFunction.showToast("비밀번호가 일치하지 않습니다.")
            }
        }

        binding.user = navArgs.data
        binding.viewModel = viewModel
        viewModel.prevData = navArgs.data
    }

    override fun initDataBinding() {
        viewModel.isCbAll.observe(viewLifecycleOwner){
            it?.let{
                viewModel.isCb0.postValue(it)
                viewModel.isCb1.postValue(it)
                viewModel.isCb2.postValue(it)
            }
        }

        viewModel.isSuccess.observe(viewLifecycleOwner){
            it?.let{
                if(it){
                    val action = SignUpPwFragmentDirections.actionSignUpPwFragmentToSignUpResultFragment()
                    navController?.navigate(action)
                }else{
                    activityFuncFunction.showToast("잠시 후 다시 시도해 주세요.")
                }
            }
        }
    }
    override fun initAfterBinding() {

    }


}