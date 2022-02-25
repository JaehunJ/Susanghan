package com.susanghan.android.ui.design.add

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.susanghan.android.databinding.LayoutDesignAddPrepareItemBinding

class PrepareItemRecyclerViewAdapter(private val dialogCallback: () -> Unit) :
    ListAdapter<PrepareItemRecyclerViewAdapter.PrepareItem, PrepareItemRecyclerViewAdapter.PrepareItemViewHolder>(
        PrepareItemDiffer()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PrepareItemViewHolder.from(parent)

    override fun onBindViewHolder(holder: PrepareItemViewHolder, position: Int) {
        holder.bind(getItem(position), getItem(position).code == -1, dialogCallback)
    }

    class PrepareItemViewHolder(private val binding: LayoutDesignAddPrepareItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): PrepareItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    LayoutDesignAddPrepareItemBinding.inflate(layoutInflater, parent, false)

                return PrepareItemViewHolder(binding)
            }
        }

        fun bind(data: PrepareItem, isLast: Boolean, dialogCallback: () -> Unit) {
            if (isLast) {
                binding.ivProduct.visibility = View.INVISIBLE
                binding.btnAdd.visibility = View.VISIBLE
                binding.root.setOnClickListener {
                    dialogCallback()
                }
            } else {
                binding.ivProduct.visibility = View.VISIBLE
                binding.btnAdd.visibility = View.GONE
            }
        }
    }

    data class PrepareItem(val code: Int)
    class PrepareItemDiffer : DiffUtil.ItemCallback<PrepareItem>() {
        override fun areItemsTheSame(
            oldItem: PrepareItem,
            newItem: PrepareItem
        ) = oldItem.code == newItem.code

        override fun areContentsTheSame(
            oldItem: PrepareItem,
            newItem: PrepareItem
        ) = oldItem == newItem

    }
}