package com.susanghan.android.ui.more.account.withdraw

import android.os.Bundle
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentWithdrawBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class WithdrawFragment:BaseFragment<FragmentWithdrawBinding, WithdrawViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_withdraw
    override val viewModel: WithdrawViewModel by viewModel()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }
}