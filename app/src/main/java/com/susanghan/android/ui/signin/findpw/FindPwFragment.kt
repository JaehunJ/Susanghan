package com.susanghan.android.ui.signin.findpw

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentFindPwBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FindPwFragment : BaseFragment<FragmentFindPwBinding, FindPwViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_find_pw
    override val viewModel: FindPwViewModel by viewModel()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.toolbar.tvTitle.text = "비밀번호 찾기"

        binding.toolbar.ivBack.setOnClickListener {
            navController?.popBackStack()
        }
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }

}