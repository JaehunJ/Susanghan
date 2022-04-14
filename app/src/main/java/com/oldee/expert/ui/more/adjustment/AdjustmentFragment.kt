package com.oldee.expert.ui.more.adjustment

import android.os.Bundle
import android.widget.PopupMenu
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.oldee.expert.R
import com.oldee.expert.base.BaseFragment
import com.oldee.expert.databinding.FragmentAdjustmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdjustmentFragment : BaseFragment<FragmentAdjustmentBinding, AdjustmentViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_adjustment
    override val viewModel: AdjustmentViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    val sort = listOf("최근 6개월", "최근 12개월", "전체")

    override fun initView(savedInstanceState: Bundle?) {
        binding.toolbar.tvTitle.text = "정산 관리"
        binding.toolbar.ivBack.setOnClickListener {
            navController?.popBackStack()
        }

        binding.clSort.setOnClickListener {
            val popup = PopupMenu(requireContext(), it)
            popup.menuInflater.inflate(R.menu.menu_adjustment_sort, popup.menu)

            popup.show()
        }

        val child = binding.llDummy.children

        child.forEach {
            it.setOnClickListener {
                navController?.navigate(AdjustmentFragmentDirections.actionAdjustmentFragmentToAdjustmentDetailFragment())
            }
        }
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }
}