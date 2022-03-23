package com.susanghan.android.ui.order

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.susanghan.android.R
import com.susanghan.android.data.ClothCategoryCode
import com.susanghan.android.data.OrderStatus
import com.susanghan.android.data.OrderType
import com.susanghan.android.databinding.LayoutOrderItemOldBinding
import com.susanghan.android.databinding.LayoutOrderSubItemBinding
import com.susanghan.android.retrofit.response.OrderListResponse.OrderData
import com.susanghan.android.retrofit.response.OrderListResponse.OrderSubData

const val COLOR_BLUE = "#6F74DD"
const val COLOR_GRAY = "#666666"
const val COLOR_RED = "#CD0600"

class OrderListAdapter(
    val navController: NavController,
    val imageCallback: (ImageView, String) -> Unit
) :
    ListAdapter<OrderData, OrderListAdapter.OrderListViewHolder>(
        OrderListDiffCallback()
    ) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListViewHolder {
        return OrderListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: OrderListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, navController, imageCallback)
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

        fun bind(
            data: OrderData,
            navController: NavController,
            imageCallback: (ImageView, String) -> Unit
        ) {
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

                        bindSubOrderView(subBinding, it, imageCallback)

                        binding.llOrders.addView(subBinding.root)
                    }
                }

                val textColor = when (data.orderStatusCode) {
                    OrderStatus.Doing.value -> COLOR_BLUE
                    OrderStatus.CarriedComplete.value -> COLOR_RED
                    else -> COLOR_GRAY
                }

                binding.tvStatus.setTextColor(Color.parseColor(textColor))
            }

            binding.root.setOnClickListener {
                val action =
                    OrderFragmentDirections.actionOrderFragmentToOrderDetailFragment(data.orderId)
                navController.navigate(action)
            }

//            binding.tvStatus.text =
        }

        fun bindSubOrderView(
            binding: LayoutOrderSubItemBinding,
            data: OrderSubData,
            imageCallback: (ImageView, String) -> Unit
        ) {
            binding.sub = data

            if (data.classCode == OrderType.R.type) {
                imageCallback(binding.ivOrderType, data.imageName)
                binding.tvOrderType.text = data.mainNm
//                binding.tvContents.text
                binding.tvContents.visibility = View.GONE
                binding.ivOrderType.setBackgroundColor(Color.parseColor("#d3d3d3"))
            } else {
                binding.ivOrderType.setBackgroundColor(Color.parseColor("#ffffff"))
                binding.tvContents.visibility = View.VISIBLE
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

        fun drawReform(data: OrderSubData) {

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
        ) = oldItem == newItem
    }
}