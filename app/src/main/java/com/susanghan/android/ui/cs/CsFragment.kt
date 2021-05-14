package com.susanghan.android.ui.cs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentCsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CsFragment : BaseFragment<FragmentCsBinding, CsViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_cs
    override val viewModel: CsViewModel by viewModel()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        val children = binding.llDummy.children

        children.forEach {
            it.setOnClickListener {
                navController?.navigate(CsFragmentDirections.actionCsFragmentToCsDetailFragment())
            }
        }
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }
}