package com.oldee.expert.ui.more.faq

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.oldee.expert.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import com.oldee.expert.R
import com.oldee.expert.databinding.FragmentFaqBinding

@AndroidEntryPoint
class FaqFragment : BaseFragment<FragmentFaqBinding, FaqViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_faq
    override val viewModel: FaqViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    lateinit var adapter: FaqListAdapter

    override fun initView(savedInstanceState: Bundle?) {
        binding.toolbar.tvTitle.text = "FAQ"
        binding.toolbar.ivBack.setOnClickListener {
            navController?.popBackStack()
        }

        adapter = FaqListAdapter {
            val action = FaqFragmentDirections.actionFaqFragmentToFaqDetailFragment(it)
            navController?.navigate(action)
        }
        binding.rvList.adapter = adapter

        binding.btnFaq.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://pf.kakao.com/_QuRxmb"))
            startActivity(intent)
        }
    }

    override fun initDataBinding() {
        viewModel.data.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
    }

    override fun initAfterBinding() {
        viewModel.requestFaqList(0,10)
    }
}