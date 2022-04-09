package com.susanghan.android.ui.more.faq.detail

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentFaqDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FaqDetailFragment:BaseFragment<FragmentFaqDetailBinding, FaqDetailViewModel, FaqDetailFragmentArgs>() {
    override val layoutId: Int = R.layout.fragment_faq_detail
    override val viewModel: FaqDetailViewModel by viewModels()
    override val navArgs: FaqDetailFragmentArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.toolbar.tvTitle.text = "FAQ"
        binding.toolbar.ivBack.setOnClickListener {
            navController?.popBackStack()
        }
        val d = navArgs.data
        binding.header.tvTitle.text = d.title
        binding.header.tvTime.text = d.creationDate
        binding.tvContents.text = d.contents
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }
}