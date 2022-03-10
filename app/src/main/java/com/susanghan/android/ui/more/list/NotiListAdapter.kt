package com.susanghan.android.ui.more.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.susanghan.android.databinding.LayoutSimpleListItemBinding
import com.susanghan.android.retrofit.response.NoticeResponse

class NotiListAdapter(val onClick:(NoticeResponse.NoticeData)->Unit) :
    androidx.recyclerview.widget.ListAdapter<NoticeResponse.NoticeData, NotiListAdapter.NotiViewHolder>(
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

        fun bind(data: NoticeResponse.NoticeData, onClick: (NoticeResponse.NoticeData) -> Unit) {
            binding.data = data
            binding.root.setOnClickListener {
                onClick(data)
            }
        }
    }

    class NotiListDiffUtil : DiffUtil.ItemCallback<NoticeResponse.NoticeData>() {
        override fun areItemsTheSame(
            oldItem: NoticeResponse.NoticeData,
            newItem: NoticeResponse.NoticeData
        ) = oldItem.noticeId == newItem.noticeId

        override fun areContentsTheSame(
            oldItem: NoticeResponse.NoticeData,
            newItem: NoticeResponse.NoticeData
        ) = oldItem == newItem
    }
}