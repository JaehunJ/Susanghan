package com.susanghan.android.ui.more.faq

import android.os.Bundle
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentFaqBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FaqFragment:BaseFragment<FragmentFaqBinding, FaqViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_faq
    override val viewModel: FaqViewModel by viewModel()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }
}