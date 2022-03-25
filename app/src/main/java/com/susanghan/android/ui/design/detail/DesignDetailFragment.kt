package com.susanghan.android.ui.design.detail

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.susanghan.android.R
import com.susanghan.android.base.BaseFragment
import com.susanghan.android.data.DESIGN_START
import com.susanghan.android.data.DESIGN_STOP
import com.susanghan.android.databinding.FragmentDesignDetailBinding
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
        reformId = navArgs.id
        adapter = DesignImageAdapter { iv, n ->
            viewModel.setImage(iv, n)
        }
        binding.vpImage.adapter = adapter

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
            viewModel.setImage(binding.ivBefore, it.beforeImageName ?: "")
            viewModel.setImage(binding.ivAfter, it.afterImageName ?: "")

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
        }

        viewModel.imageList.observe(viewLifecycleOwner) {
            adapter.submitList(it)

            binding.indicator.setViewPager2(binding.vpImage)
            binding.indicator.refreshDots()
//            binding.indicator.notifySubtreeAccessibilityStateChanged()
        }
    }

    override fun initAfterBinding() {
        viewModel.requestDesignDetail(reformId)
    }

    private fun showStartDialog() {
        val builder = AlertDialog.Builder(requireContext()).apply {
            setTitle("판매중으로 변경하시겠습니까?")
            setMessage("사용자들이 구매를 하거나 문의를 남길 수 있습니다.\n" + "판매를 시작한 디자인은 수정 및 삭제가 불가능합니다.")
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
        val builder = AlertDialog.Builder(requireContext()).apply {
            setTitle("판매증지로 변경하시겠습니까?")
            setMessage("사용자들에게 이 디자인을 공개하지 않습니다.")
            setPositiveButton("판매중지") { d, t ->
                d.dismiss()
                viewModel.requestDesignDetailStateUpdate(reformId, DESIGN_STOP)
            }
            setNegativeButton("취소") { d, t ->
                d.dismiss()
            }
        }

        builder.create().show()
    }
}