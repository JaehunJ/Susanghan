package com.susanghan.android.ui.order

import android.os.Bundle
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentOrderBinding
import com.susanghan.android.databinding.FragmentOrderOldBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderFragment:BaseFragment<FragmentOrderOldBinding, OrderViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_order_old
    override val viewModel: OrderViewModel by viewModel()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }
}