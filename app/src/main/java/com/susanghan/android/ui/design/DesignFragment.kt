package com.susanghan.android.ui.design

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.tabs.TabLayout
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.data.ReformStatus
import com.susanghan.android.databinding.FragmentDesignBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DesignFragment : BaseFragment<FragmentDesignBinding, DesignViewModel, NavArgs>(), SwipeRefreshLayout.OnRefreshListener {
    override val layoutId: Int = R.layout.fragment_design
    override val viewModel: DesignViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.llBlankItem.visibility = View.VISIBLE
        binding.llExistItem.visibility = View.GONE
        binding.swList.setOnRefreshListener(this)
    }

    override fun initDataBinding() {
        val adapter = DesignListAdapter(findNavController()) { v, u ->
            viewModel.setImage(v, u)
        }

        viewModel.designList.observe(viewLifecycleOwner) {
            it?.let {
                if (it.isNullOrEmpty() && viewModel.page == 0) {
                    binding.llBlankItem.visibility = View.VISIBLE
                    binding.llExistItem.visibility = View.GONE
                    adapter.submitList(it)
                }else if(!(it.isNullOrEmpty() && viewModel.page != 0)) {
                    binding.llBlankItem.visibility = View.GONE
                    binding.llExistItem.visibility = View.VISIBLE
                    adapter.submitList(it)
                }
            }
        }

        binding.rvList.adapter = adapter
        binding.btnDesignAdd.setOnClickListener {
            val action = DesignFragmentDirections.actionDesignFragmentToDesignAddFragment()
            navController?.navigate(action)
        }
        binding.btnDesignAdd1.setOnClickListener {
            val action = DesignFragmentDirections.actionDesignFragmentToDesignAddFragment()
            navController?.navigate(action)
        }

        binding.topTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val pos = tab?.position

                pos?.let { p ->
                    val status = when (pos) {
                        0 -> ReformStatus.None
                        1 -> ReformStatus.Start
                        else -> ReformStatus.Stop
                    }
                    viewModel.requestDesignList(0, 10, status.value)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    override fun initAfterBinding() {
        viewModel.requestDesignList(0, 10, ReformStatus.None.value)
    }

    override fun onRefresh() {
        Log.e("#debug", "refresh")
        viewModel.requestDesignList(viewModel.page+1, 10, viewModel.reformStatus)
        binding.swList.isRefreshing = false
    }
}