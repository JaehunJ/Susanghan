package com.susanghan.android.ui.order.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.susanghan.android.databinding.LayoutOrderDetailItemBinding
import com.susanghan.android.retrofit.response.OrderDetailResponse

class OrderDetailSubAdapter(val imageCallback:(ImageView, String)->Unit) :
    ListAdapter<OrderDetailResponse.OrderDetailSub, OrderDetailSubAdapter.OrderDetailSubViewHolder>(
        OrderDetailDiffUtil()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        OrderDetailSubViewHolder.from(parent)

    override fun onBindViewHolder(holder: OrderDetailSubViewHolder, position: Int) {
        holder.bind(getItem(position), imageCallback)
    }

    class OrderDetailSubViewHolder(val binding: LayoutOrderDetailItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): OrderDetailSubViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutOrderDetailItemBinding.inflate(layoutInflater, parent, false)

                return OrderDetailSubViewHolder(binding)
            }
        }

        fun bind(data: OrderDetailResponse.OrderDetailSub,imageCallback:(ImageView, String)->Unit) {
            binding.res = data
            imageCallback(binding.ivImage, data.imageName?:"")

            var prepareItem = ""
            data.subNmList?.forEachIndexed { index, s ->
                prepareItem += s
                if(index != data.subNmList.count()-1){
                    prepareItem += ", "
                }
            }

            binding.tvPrepareItem.text = prepareItem
        }
    }

    class OrderDetailDiffUtil : DiffUtil.ItemCallback<OrderDetailResponse.OrderDetailSub>() {
        override fun areItemsTheSame(
            oldItem: OrderDetailResponse.OrderDetailSub,
            newItem: OrderDetailResponse.OrderDetailSub
        ) = oldItem.orderDetailId == newItem.orderDetailId

        override fun areContentsTheSame(
            oldItem: OrderDetailResponse.OrderDetailSub,
            newItem: OrderDetailResponse.OrderDetailSub
        ) = oldItem == newItem
    }
}