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
import com.susanghan.android.base.BaseViewModel
import com.susanghan.android.databinding.FragmentFindIdBinding
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