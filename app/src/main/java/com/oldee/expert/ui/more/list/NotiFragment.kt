package com.oldee.expert.ui.more.list

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.oldee.expert.R
import com.oldee.expert.base.BaseFragment
import com.oldee.expert.databinding.FragmentNotiBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotiFragment : BaseFragment<FragmentNotiBinding, NotiViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_noti
    override val viewModel: NotiViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    lateinit var adapter: NotiListAdapter

    override fun initView(savedInstanceState: Bundle?) {
        binding.toolbar.tvTitle.text = "공지사항"
        binding.toolbar.ivBack.setOnClickListener {
            navController?.popBackStack()
        }

        adapter = NotiListAdapter {
            navController?.navigate(NotiFragmentDirections.actionNotiFragmentToNotiDetailFragment(it))
        }

        binding.rvList.adapter = adapter
    }

    override fun initDataBinding() {
        viewModel.notiData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun initAfterBinding() {
        viewModel.requestNotice(0)
    }
}