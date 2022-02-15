package com.susanghan.android.ui.design.add

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentDesignAddBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DesignAddFragment : BaseFragment<FragmentDesignAddBinding, DesignAddViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_design_add
    override val viewModel: DesignAddViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }
}