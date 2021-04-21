package com.susanghan.android.ui.order.detail

import android.os.Bundle
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentOrderDetailBinding
import com.susanghan.android.ui.order.OrderViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderDetailFragment : BaseFragment<FragmentOrderDetailBinding, OrderViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_order_detail
    override val viewModel: OrderViewModel by viewModel()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }

}