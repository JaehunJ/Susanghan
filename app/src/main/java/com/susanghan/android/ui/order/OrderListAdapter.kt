package com.susanghan.android.ui.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.susanghan.android.R
import com.susanghan.android.data.ClothCategoryCode
import com.susanghan.android.databinding.LayoutOrderItemOldBinding
import com.susanghan.android.databinding.LayoutOrderSubItemBinding
import com.susanghan.android.retrofit.response.OrderListResponse.OrderSubData
import com.susanghan.android.retrofit.response.OrderListResponse.OrderData

class OrderListAdapter(val navController: NavController) :
    ListAdapter<OrderData, OrderListAdapter.OrderListViewHolder>(
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

        fun bind(data: OrderData, navController: NavController) {
            binding.order = data

            data.let { d ->
                val list = data.orderList

                if (list.isNotEmpty()) {
                    val inflater = LayoutInflater.from(binding.root.context)
                    list.forEach {
                        val subBinding = LayoutOrderSubItemBinding.inflate(
                            inflater,
                            binding.root as ViewGroup,
                            false
                        )

                        bindSubOrderView(subBinding, it)

                        binding.llOrders.addView(subBinding.root)
                    }
                }
            }

            binding.root.setOnClickListener {
                val action = OrderFragmentDirections.actionOrderFragmentToOrderDetailFragment()
                navController.navigate(action)
            }
        }

        fun bindSubOrderView(
            binding: LayoutOrderSubItemBinding,
            data: OrderSubData
        ) {
            binding.sub = data
//            binding.tvOrderType.text = data.mainNm
//            binding.tvContents.text = "옵션 : ${data.subNm}"

            val imgRes = when (data.mainCode) {
                ClothCategoryCode.Sweatshirt.value -> {
                    R.drawable.icon_clothes_upper
                }
                ClothCategoryCode.Shirts.value -> {
                    R.drawable.icon_clothes_upper
                }
                ClothCategoryCode.One.value -> {
                    R.drawable.icon_clothes_bottom
                }
                ClothCategoryCode.Pants.value -> {
                    R.drawable.icon_clothes_bottom
                }
                ClothCategoryCode.Jean.value -> {
                    R.drawable.icon_clothes_bottom
                }
                ClothCategoryCode.Skirts.value -> {
                    R.drawable.icon_clothes_bottom
                }
                else -> {
                    R.drawable.icon_clothes_bottom
                }
            }

            binding.ivOrderType.setImageResource(imgRes)
        }
    }

    class OrderListDiffCallback : DiffUtil.ItemCallback<OrderData>() {
        override fun areItemsTheSame(
            oldItem: OrderData,
            newItem: OrderData
        ) = oldItem.orderNum == newItem.orderNum

        override fun areContentsTheSame(
            oldItem: OrderData,
            newItem: OrderData
        ) =  oldItem == newItem
    }
}