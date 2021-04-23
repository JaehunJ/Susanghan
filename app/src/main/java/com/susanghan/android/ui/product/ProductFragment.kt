package com.susanghan.android.ui.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentProductBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductFragment : BaseFragment<FragmentProductBinding, ProductViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_product
    override val viewModel: ProductViewModel by viewModel()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }

}