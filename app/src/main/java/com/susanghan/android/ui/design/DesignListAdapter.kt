package com.susanghan.android.ui.design

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.susanghan.android.databinding.LayoutDesignItemBinding

class DesignListAdapter :
    ListAdapter<DesignListAdapter.TempDesignItem, DesignListAdapter.DesignItemViewHolder>(
        DesignItemDiffCallback()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DesignItemViewHolder {
        return DesignItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DesignItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class DesignItemViewHolder(val binding: LayoutDesignItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): DesignItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutDesignItemBinding.inflate(layoutInflater, parent, false)
                return DesignItemViewHolder(binding)
            }
        }

        fun bind(data: TempDesignItem) {

        }
    }

    class DesignItemDiffCallback : DiffUtil.ItemCallback<TempDesignItem>() {
        override fun areItemsTheSame(oldItem: TempDesignItem, newItem: TempDesignItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TempDesignItem, newItem: TempDesignItem): Boolean {
            return oldItem == newItem
        }

    }

    data class TempDesignItem(val imagePath: String, val title: String, val like: Int, val id: Int)


}