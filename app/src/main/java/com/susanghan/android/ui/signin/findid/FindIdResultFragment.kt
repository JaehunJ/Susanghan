package com.susanghan.android.ui.signin.findid

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
import com.susanghan.android.databinding.FragmentFindIdBinding
import com.susanghan.android.databinding.FragmentFindIdResultBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FindIdResultFragment : BaseFragment<FragmentFindIdResultBinding, FindIdResultViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_find_id_result
    override val viewModel: FindIdResultViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.toolbar.tvTitle.text = "아이디 찾기"

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