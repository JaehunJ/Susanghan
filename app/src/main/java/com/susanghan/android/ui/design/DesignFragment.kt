package com.susanghan.android.ui.design

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.databinding.FragmentDesignBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DesignFragment : BaseFragment<FragmentDesignBinding, DesignViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_design
    override val viewModel: DesignViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initDataBinding() {
        val adapter = DesignListAdapter(findNavController()) { v, u ->
            viewModel.setImage(v, u)
        }

        viewModel.designList.observe(viewLifecycleOwner) {
            it?.let{
                if(it.isEmpty()){
                    binding.llBlankItem.visibility = View.VISIBLE
                    binding.llExistItem.visibility = View.GONE
                }else{
                    binding.llBlankItem.visibility = View.GONE
                    binding.llExistItem.visibility = View.VISIBLE
                }
            }
            adapter.submitList(it)
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
        viewModel.requestDesignList(0, 10, 0)
    }
}