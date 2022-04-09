package com.susanghan.android.ui.more.list.detail

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentNotiDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotiDetailFragment :
    BaseFragment<FragmentNotiDetailBinding, NotiDetailViewModel, NotiDetailFragmentArgs>() {
    override val layoutId: Int = R.layout.fragment_noti_detail
    override val viewModel: NotiDetailViewModel by viewModels()
    override val navArgs: NotiDetailFragmentArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.toolbar.tvTitle.text = "공지사항"
        binding.toolbar.ivBack.setOnClickListener {
            navController?.popBackStack()
        }
//        navArgs.data
        val d = navArgs.data
        binding.header.tvTitle.text = d.title
        binding.header.tvTime.text = d.creationDate
        binding.tvContents.text = d.contents

    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }
}