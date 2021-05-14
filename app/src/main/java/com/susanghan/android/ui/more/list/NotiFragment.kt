package com.susanghan.android.ui.more.list

import android.os.Bundle
import androidx.core.view.children
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentNotiBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotiFragment:BaseFragment<FragmentNotiBinding, NotiViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_noti
    override val viewModel: NotiViewModel by viewModel()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.toolbar.tvTitle.text = "공지사항"
        binding.toolbar.ivBack.setOnClickListener {
            navController?.popBackStack()
        }

        val children = binding.llDummy.children

        children.forEach {
            it.setOnClickListener {
                navController?.navigate(NotiFragmentDirections.actionNotiFragmentToNotiDetailFragment())
            }
        }
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }
}