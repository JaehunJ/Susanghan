package com.susanghan.android.ui.more.faq

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.susanghan.android.databinding.LayoutSimpleListItemBinding
import com.susanghan.android.retrofit.response.FaqData
import com.susanghan.android.retrofit.response.FaqResponse

class FaqListAdapter(val onClick: (FaqData) -> Unit):ListAdapter<FaqData, FaqListAdapter.FaqItemViewHolder>(
    FaqDiffUtil()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FaqItemViewHolder.from(parent)

    override fun onBindViewHolder(holder: FaqItemViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

    class FaqItemViewHolder(val binding: LayoutSimpleListItemBinding):RecyclerView.ViewHolder(binding.root){
        companion object{
            fun from(viewGroup: ViewGroup):FaqItemViewHolder{
                val layoutInflater = LayoutInflater.from(viewGroup.context)
                val binding = LayoutSimpleListItemBinding.inflate(layoutInflater, viewGroup, false)

                return FaqItemViewHolder(binding)
            }
        }

        fun bind(data:FaqData, onClick:(FaqData)->Unit){
            binding.tvTitle.text = data.title
            binding.tvTime.text = data.creationDate
            binding.root.setOnClickListener {
                onClick(data)
            }
        }
    }

    class FaqDiffUtil:DiffUtil.ItemCallback<FaqData>(){
        override fun areItemsTheSame(
            oldItem: FaqData,
            newItem: FaqData
        ) = oldItem.faqId == newItem.faqId

        override fun areContentsTheSame(
            oldItem: FaqData,
            newItem: FaqData
        ) = oldItem == newItem
    }

}