package com.oldee.expert.ui.design

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.tabs.TabLayout
import com.oldee.expert.R
import com.oldee.expert.base.BaseFragment
import com.oldee.expert.custom.OnScrollEndListener
import com.oldee.expert.data.ReformStatus
import com.oldee.expert.databinding.FragmentDesignBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DesignFragment : BaseFragment<FragmentDesignBinding, DesignViewModel, NavArgs>(),
    SwipeRefreshLayout.OnRefreshListener, TabLayout.OnTabSelectedListener {
    override val layoutId: Int = R.layout.fragment_design
    override val viewModel: DesignViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    var enablePostScroll = false

    override fun initView(savedInstanceState: Bundle?) {
        binding.llBlankItem.visibility = View.VISIBLE
        binding.llExistItem.visibility = View.GONE
        binding.swList.setOnRefreshListener(this)
        binding.rvList.addOnScrollListener(OnScrollEndListener {
            addItem()
        })


        binding.topTab.addOnTabSelectedListener(this)
    }

    override fun initDataBinding() {
        val adapter = DesignListAdapter(findNavController()) { v, u ->
            viewModel.setImage(v, u)
        }
//        adapter.registerAdapterDataObserver(object :RecyclerView.AdapterDataObserver(){
//            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
//                super.onItemRangeInserted(positionStart, itemCount)
//            }
//        })

        navController?.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("post_design")
            ?.observe(
                viewLifecycleOwner
            ) {
                it?.let {
                    if (it.isNotEmpty()) {
                        enablePostScroll = true
                        Log.e("#debug", "get post_design msg")

//                        binding.swList.scrollTo(0, 0)
                    }
                }
            }

        viewModel.designList.observe(viewLifecycleOwner) {
            it?.let {
                if (it.isNullOrEmpty() && viewModel.page == 0) {
                    binding.llBlankItem.visibility = View.VISIBLE
                    binding.llExistItem.visibility = View.GONE
                    adapter.submitList(it)
                } else if (it.isEmpty() && viewModel.page != 0) {

                } else {
                    binding.llBlankItem.visibility = View.GONE
                    binding.llExistItem.visibility = View.VISIBLE
                    adapter.submitList(it) {
                        Log.e("#debug", "call back")
                        if (viewModel.page == 0) {
                            binding.rvList.post {
                                Log.e("#debug", " post call back")
                                binding.rvList.smoothScrollToPosition(0)
                            }
                        }
                    }
                }
//
//                if(enablePostScroll){
//                    enablePostScroll = false
//                    Log.e("#debug", "scroll top")
//                    viewModel.postDelay({
//
//                    },100)
//                }
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

    }

    override fun initAfterBinding() {
        viewModel.requestDesignList(0, 10, ReformStatus.None.value)
    }

    override fun onRefresh() {
        viewModel.requestDesignList(viewModel.page, 10, viewModel.reformStatus)
        binding.swList.isRefreshing = false
    }

    fun addItem() {
        viewModel.requestDesignList(viewModel.page + 1, 10, viewModel.reformStatus, true)
    }

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
}