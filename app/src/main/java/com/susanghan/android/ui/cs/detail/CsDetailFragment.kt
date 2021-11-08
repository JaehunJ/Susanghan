package com.susanghan.android.ui.cs.detail

import android.os.Bundle
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentCsDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CsDetailFragment:BaseFragment<FragmentCsDetailBinding, CsDetailViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_cs_detail
    override val viewModel: CsDetailViewModel by viewModel()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.toolbar.tvTitle.text = "고객 문의"
        binding.toolbar.ivBack.setOnClickListener {
            navController?.popBackStack()
        }
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }

}