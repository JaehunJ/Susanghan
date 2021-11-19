package com.susanghan.android.ui.repairer

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentRepairerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepairerFragment : BaseFragment<FragmentRepairerBinding, RepairerViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_repairer
    override val viewModel: RepairerViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }

}