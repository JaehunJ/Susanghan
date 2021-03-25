package com.susanghan.android.ui.more.list

import android.os.Bundle
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

    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }
}