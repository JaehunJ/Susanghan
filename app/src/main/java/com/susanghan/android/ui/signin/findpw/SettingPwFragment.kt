package com.susanghan.android.ui.signin.findpw

import android.os.Bundle
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentSettingPwBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingPwFragment : BaseFragment<FragmentSettingPwBinding, SettingPwViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_setting_pw
    override val viewModel: SettingPwViewModel by viewModel()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.toolbar.tvTitle.text = "비밀번호 재설정"

        binding.toolbar.ivBack.setOnClickListener {
            navController?.popBackStack()
        }

        binding.btnConfirm.setOnClickListener {
            navController?.popBackStack()
        }
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }

}