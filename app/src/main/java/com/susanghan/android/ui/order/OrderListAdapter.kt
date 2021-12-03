package com.susanghan.android.ui.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.susanghan.android.databinding.LayoutOrderItemOldBinding
import com.susanghan.android.retrofit.response.OrderListResponse

class OrderListAdapter(val navController: NavController) :
    ListAdapter<OrderListResponse.OrderData, OrderListAdapter.OrderListViewHolder>(
        OrderListDiffCallback()
    ) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListViewHolder {
        return OrderListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: OrderListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, navController)
    }

    class OrderListViewHolder(val binding: LayoutOrderItemOldBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): OrderListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutOrderItemOldBinding.inflate(layoutInflater, parent, false)

                return OrderListViewHolder(binding)
            }
        }

        fun bind(data: OrderListResponse.OrderData, navController: NavController) {
            data.run {
                binding.tvOrderType.text = this.mainCategory
                binding.tvContents.text = this.itemCategory
                binding.tvStatus.text = this.oStatus
                binding.tvPrice.text = this.oTotalPrice.toString()
            }

            binding.root.setOnClickListener {
                val action = OrderFragmentDirections.actionOrderFragmentToOrderDetailFragment()
                navController.navigate(action)
            }
        }
    }

    class OrderListDiffCallback : DiffUtil.ItemCallback<OrderListResponse.OrderData>() {
        override fun areItemsTheSame(
            oldItem: OrderListResponse.OrderData,
            newItem: OrderListResponse.OrderData
        ): Boolean {
            return oldItem.orderSeq == newItem.orderSeq
        }

        override fun areContentsTheSame(
            oldItem: OrderListResponse.OrderData,
            newItem: OrderListResponse.OrderData
        ): Boolean {
            return oldItem == newItem
        }
    }
}