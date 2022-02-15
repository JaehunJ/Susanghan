package com.susanghan.android.ui.design

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.susanghan.android.base.BaseRepository
import com.susanghan.android.base.BaseViewModel
import com.susanghan.android.databinding.LayoutDesignItemBinding
import com.susanghan.android.retrofit.response.DesignListResponse.DesignData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

class DesignListAdapter(val navController: NavController, val imageCallback:(ImageView, String)->Unit) :
    ListAdapter<DesignData, DesignListAdapter.DesignItemViewHolder>(
        DesignItemDiffCallback()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DesignItemViewHolder {
        return DesignItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DesignItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, navController, imageCallback)
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

        fun bind(data: DesignData, navController: NavController, imageCallback:(ImageView, String)->Unit) {
            binding.design = data
            imageCallback(binding.ivProduct, data.imageName)
            binding.root.setOnClickListener {
                val action = DesignFragmentDirections.actionDesignFragmentToDesignDetailFragment(data.reformId)
                navController.navigate(action)
            }
        }
    }

    class DesignItemDiffCallback : DiffUtil.ItemCallback<DesignData>() {
        override fun areItemsTheSame(
            oldItem: DesignData,
            newItem: DesignData
        ) = oldItem.reformId == newItem.reformId


        override fun areContentsTheSame(
            oldItem: DesignData,
            newItem: DesignData
        ) = oldItem == newItem
    }
}