package com.susanghan.android.ui.design

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentDesignBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Single
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class DesignFragment : BaseFragment<FragmentDesignBinding, DesignViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_design
    override val viewModel: DesignViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initDataBinding() {
        val adapter = DesignListAdapter()

        viewModel.designList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.rvList.adapter = adapter
    }

    override fun initAfterBinding() {
        viewModel.requestDesignList()
    }

}