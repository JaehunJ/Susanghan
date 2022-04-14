package com.oldee.expert.ui.signin.findpw

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.oldee.expert.R
import com.oldee.expert.base.BaseFragment
import com.oldee.expert.databinding.FragmentFindPwBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FindPwFragment : BaseFragment<FragmentFindPwBinding, FindPwViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_find_pw
    override val viewModel: FindPwViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.toolbar.tvTitle.text = "비밀번호 찾기"

        binding.toolbar.ivBack.setOnClickListener {
            navController?.popBackStack()
        }

        binding.btnConfirm.setOnClickListener {
            val action = FindPwFragmentDirections.actionFindPwFragmentToSettingPwFragment()
            navController?.navigate(action)
        }
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }

}