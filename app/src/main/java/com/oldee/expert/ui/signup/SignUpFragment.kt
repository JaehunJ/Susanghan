package com.oldee.expert.ui.signup

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.oldee.expert.BuildConfig
import com.oldee.expert.R
import com.oldee.expert.base.BaseFragment
import com.oldee.expert.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding, SignUpViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_sign_up
    override val viewModel: SignUpViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.btnConfirm.setOnClickListener {
            if (viewModel.getValidated()) {
                viewModel.requestConfirm()
            } else {
                activityFuncFunction.showToast("누락된 정보가 있습니다.")
            }
        }

        binding.btnOpenStore.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.STORE_URL))
            startActivity(intent)
        }
    }

    override fun initDataBinding() {
        viewModel.confirm.observe(viewLifecycleOwner) {
            it?.let{
                if(it){
                    val action =
                        SignUpFragmentDirections.actionSignUpFragmentToSignUpPwFragment(viewModel.getInfo())
                    navController?.navigate(action)
                }else{
                    val builder = AlertDialog.Builder(requireContext()).apply {
                        setTitle("오류")
                        setMessage("이미 가입된 정보입니다.")
                        setPositiveButton("확인") { d, t ->
                            d.dismiss()
                        }
                    }

                    builder.create().show()
                }
            }
        }

        binding.data = viewModel
    }

    override fun initAfterBinding() {

    }
}