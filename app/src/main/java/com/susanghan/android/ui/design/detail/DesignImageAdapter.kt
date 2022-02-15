package com.susanghan.android.ui.design.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.susanghan.android.databinding.LayoutDesignItemDetailBigBinding

class DesignImageAdapter(val imageCallBack:(ImageView, String)->Unit) :
    ListAdapter<DesignImageAdapter.DesignDetailBigImage, DesignImageAdapter.DesignImageViewHolder>(
        DesignDetailBigImageDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DesignImageViewHolder.from(parent)

    override fun onBindViewHolder(holder: DesignImageViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, imageCallBack)
    }

    class DesignDetailBigImageDiffUtil : DiffUtil.ItemCallback<DesignDetailBigImage>() {
        override fun areItemsTheSame(
            oldItem: DesignDetailBigImage,
            newItem: DesignDetailBigImage
        ) = oldItem.name == newItem.name

        override fun areContentsTheSame(
            oldItem: DesignDetailBigImage,
            newItem: DesignDetailBigImage
        ) = oldItem == newItem

    }


    class DesignImageViewHolder(val binding: LayoutDesignItemDetailBigBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parents: ViewGroup): DesignImageViewHolder {
                val inflater = LayoutInflater.from(parents.context)
                val binding = LayoutDesignItemDetailBigBinding.inflate(inflater, parents, false)

                return DesignImageViewHolder(binding)
            }
        }

        fun bind(data:DesignDetailBigImage, itemCallback:(ImageView, String)->Unit) {
            itemCallback.invoke(binding.ivDesign, data.name)
        }
    }

    data class DesignDetailBigImage(val name: String)
}