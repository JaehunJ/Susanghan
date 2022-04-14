package com.oldee.expert.ui.signin.findid

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.oldee.expert.R
import com.oldee.expert.base.BaseFragment
import com.oldee.expert.databinding.FragmentFindIdBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FindIdFragment : BaseFragment<FragmentFindIdBinding, FindIdViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_find_id
    override val viewModel: FindIdViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.toolbar.tvTitle.text = "아이디 찾기"

        binding.toolbar.ivBack.setOnClickListener {
            navController?.popBackStack()
        }

        binding.btnConfirm.setOnClickListener {
            val action = FindIdFragmentDirections.actionFindIdFragmentToFindIdResultFragment()
            navController?.navigate(action)
        }
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }

}