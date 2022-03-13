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
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding, SignUpViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_sign_up
    override val viewModel: SignUpViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.btnConfirm.setOnClickListener {
            if(viewModel.getValidated()){
                viewModel.requestConfirm()
            }else{
                activityFuncFunction.showToast("누락된 정보가 있습니다.")
            }
        }
    }

    override fun initDataBinding() {
        viewModel.confirm.observe(viewLifecycleOwner){
            val action = SignUpFragmentDirections.actionSignUpFragmentToSignUpPwFragment(viewModel.getInfo())
            navController?.navigate(action)
        }

        binding.data = viewModel
    }

    override fun initAfterBinding() {

    }
}