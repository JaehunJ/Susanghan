package com.oldee.expert.ui.design.detail

import android.graphics.Paint
import android.os.Bundle
import android.util.Size
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.oldee.expert.R
import com.oldee.expert.base.BaseFragment
import com.oldee.expert.data.DESIGN_START
import com.oldee.expert.data.DESIGN_STOP
import com.oldee.expert.databinding.FragmentDesignDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DesignDetailFragment :
    BaseFragment<FragmentDesignDetailBinding, DesignDetailViewModel, DesignDetailFragmentArgs>() {
    override val layoutId: Int = R.layout.fragment_design_detail
    override val viewModel: DesignDetailViewModel by viewModels()
    override val navArgs: DesignDetailFragmentArgs by navArgs()

    private var reformId = 0
    private lateinit var adapter: DesignImageAdapter
    private lateinit var smallAdapter: DesignItemImageAdapter

    override fun initView(savedInstanceState: Bundle?) {
        binding.toolbar.tvTitle.text = "디자인 상세"
        binding.toolbar.ivBack.setOnClickListener {
            navController?.previousBackStackEntry?.savedStateHandle?.set("post_design", "success")
            navController?.popBackStack()
        }
        reformId = navArgs.id

        smallAdapter = DesignItemImageAdapter(requireContext())
        binding.rvItemSmall.adapter = smallAdapter

        binding.btnStop.setOnClickListener {
            showStopDialog()
        }

        binding.btnStart.setOnClickListener {
            showStartDialog()
        }

        binding.btnModify.setOnClickListener {
            val action =
                DesignDetailFragmentDirections.actionDesignDetailFragmentToDesignAddFragment(
                    reformId
                )

            navController?.navigate(action)
        }

        binding.tvTitleInfo.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.tvTitleBa.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.tvTitlePrepare.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.tvTitleDay.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }

    override fun initDataBinding() {
        viewModel.data.observe(viewLifecycleOwner) {
            viewModel.setImage(binding.ivBefore, it.beforeImageName ?: "", Size(400, 400))
            viewModel.setImage(binding.ivAfter, it.afterImageName ?: "", Size(400, 400))

            binding.detail = viewModel.data.value
            smallAdapter.submitList(it.items)

            when (it.status) {
                0 -> {
                    binding.btnModify.visibility = View.VISIBLE
                    binding.btnStart.visibility = View.VISIBLE
                    binding.btnStop.visibility = View.GONE
                    binding.btnBlank.visibility = View.GONE
                }
                2 -> {
                    binding.btnModify.visibility = View.GONE
                    binding.btnStart.visibility = View.GONE
                    binding.btnStop.visibility = View.VISIBLE
                    binding.btnBlank.visibility = View.VISIBLE
                }
                else -> {
                    binding.btnModify.visibility = View.GONE
                    binding.btnStart.visibility = View.VISIBLE
                    binding.btnStop.visibility = View.GONE
                    binding.btnBlank.visibility = View.VISIBLE
                }
            }

            //text

            binding.tvDate.text =
                String.format(getString(R.string.design_detail_date), it.minDay, it.maxDay)
//                "택배 수령 후 ${it.minDay}-${it.maxDay}일정도의 제작기간이 소요됩니다.\n제작 완료 시 택배사 사정에 따라 배송시간이 달라질 수 있습니다"
        }

        viewModel.imageList.observe(viewLifecycleOwner) {
            adapter = DesignImageAdapter { iv, n ->
                viewModel.setImage(iv, n)
            }
            binding.vpImage.adapter = adapter

            adapter.submitList(it)

            binding.indicator.setViewPager2(binding.vpImage)
            binding.indicator.refreshDots()
        }

    }

    override fun initAfterBinding() {
        viewModel.requestDesignDetail(reformId)
    }

    private fun showStartDialog() {
        val builder = MaterialAlertDialogBuilder(requireContext(), R.style.CommonCustomDialog).apply {
            setTitle(R.string.dialog_design_detail_start_title)
            setMessage(R.string.dialog_design_detail_start_contents)
            setPositiveButton("판매시작") { d, t ->
                d.dismiss()
                viewModel.requestDesignDetailStateUpdate(reformId, DESIGN_START)
            }
            setNegativeButton("취소") { d, t ->
                d.dismiss()
            }
        }

        builder.create().show()
    }

    private fun showStopDialog() {
        val builder = MaterialAlertDialogBuilder(requireContext(), R.style.CommonCustomDialog).apply {
            setTitle(R.string.dialog_design_detail_stop_title)
            setMessage(R.string.dialog_design_detail_stop_contents)
            setPositiveButton("판매중지") { d, t ->
                d.dismiss()
                viewModel.requestDesignDetailStateUpdate(reformId, DESIGN_STOP) {
                    activityFuncFunction.showToast("주문중인 상품이 있습니다.")
                }
            }
            setNegativeButton("취소") { d, t ->
                d.dismiss()
            }
        }

        builder.create().show()
    }
}