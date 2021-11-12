package com.susanghan.android.ui.more.list.detail

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentNotiDetailBinding


class NotiDetailFragment:BaseFragment<FragmentNotiDetailBinding, NotiDetailViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_noti_detail
    override val viewModel: NotiDetailViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.toolbar.tvTitle.text = "공지사항"
        binding.toolbar.ivBack.setOnClickListener {
            navController?.popBackStack()
        }
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }
}