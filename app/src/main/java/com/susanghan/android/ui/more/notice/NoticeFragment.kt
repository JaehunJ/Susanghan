package com.susanghan.android.ui.more.notice

import android.os.Bundle
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentNotiBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class NoticeFragment:BaseFragment<FragmentNotiBinding, NoticeViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_notice
    override val viewModel: NoticeViewModel by viewModel()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }
}