package com.susanghan.android.ui.design.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.susanghan.android.databinding.LayoutDesignItemDetailSmallBinding
import com.susanghan.android.retrofit.response.DesignDetailResponse

class DesignItemImageAdapter() :
    ListAdapter<DesignDetailResponse.DesignDetailItem, DesignItemImageAdapter.DesignDetailItemSmallViewHolder>(
        DesignItemSmallDiffUtil()
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = DesignDetailItemSmallViewHolder.from(parent)

    override fun onBindViewHolder(holder: DesignDetailItemSmallViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    class DesignDetailItemSmallViewHolder(val binding: LayoutDesignItemDetailSmallBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): DesignDetailItemSmallViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    LayoutDesignItemDetailSmallBinding.inflate(layoutInflater, parent, false)
                return DesignDetailItemSmallViewHolder(binding)
            }
        }

        fun bind(data: DesignDetailResponse.DesignDetailItem) {
            binding.tvName.text = data.itemName
        }
    }

    class DesignItemSmallDiffUtil : DiffUtil.ItemCallback<DesignDetailResponse.DesignDetailItem>() {
        override fun areItemsTheSame(
            oldItem: DesignDetailResponse.DesignDetailItem,
            newItem: DesignDetailResponse.DesignDetailItem
        ) = oldItem.itemId == newItem.itemId

        override fun areContentsTheSame(
            oldItem: DesignDetailResponse.DesignDetailItem,
            newItem: DesignDetailResponse.DesignDetailItem
        ) = oldItem == newItem

    }
}