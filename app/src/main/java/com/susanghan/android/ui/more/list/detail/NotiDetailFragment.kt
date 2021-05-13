package com.susanghan.android.ui.more.list.detail

import android.os.Bundle
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentNotiDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotiDetailFragment:BaseFragment<FragmentNotiDetailBinding, NotiDetailViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_noti_detail
    override val viewModel: NotiDetailViewModel by viewModel()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }
}