package com.susanghan.android.ui.more.adjustment

import android.os.Bundle
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentAdjustmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AdjustmentFragment:BaseFragment<FragmentAdjustmentBinding, AdjustmentViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_adjustment
    override val viewModel: AdjustmentViewModel by viewModel()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }
}