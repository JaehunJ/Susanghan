package com.susanghan.android.ui.more.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.susanghan.android.databinding.LayoutSimpleListItemBinding
import com.susanghan.android.retrofit.response.NoticeData
import com.susanghan.android.retrofit.response.NoticeResponse

class NotiListAdapter(val onClick: (NoticeData) -> Unit) :
    androidx.recyclerview.widget.ListAdapter<NoticeData, NotiListAdapter.NotiViewHolder>(
        NotiListDiffUtil()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NotiViewHolder.from(parent)

    override fun onBindViewHolder(holder: NotiViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

    class NotiViewHolder(val binding: LayoutSimpleListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(viewGroup: ViewGroup): NotiViewHolder {
                val layoutInflater = LayoutInflater.from(viewGroup.context)
                val binding = LayoutSimpleListItemBinding.inflate(layoutInflater, viewGroup, false)

                return NotiViewHolder(binding)
            }
        }

        fun bind(data: NoticeData, onClick: (NoticeData) -> Unit) {
//            binding.data = data
            binding.tvTitle.text = data.title
            binding.tvTime.text = data.creationDate
            binding.root.setOnClickListener {
                onClick(data)
            }
        }
    }

    class NotiListDiffUtil : DiffUtil.ItemCallback<NoticeData>() {
        override fun areItemsTheSame(
            oldItem: NoticeData,
            newItem: NoticeData
        ) = oldItem.noticeId == newItem.noticeId

        override fun areContentsTheSame(
            oldItem: NoticeData,
            newItem: NoticeData
        ) = oldItem == newItem
    }
}