package com.oldee.expert.ui.order

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.ColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.oldee.expert.R
import com.oldee.expert.data.ClothCategoryCode
import com.oldee.expert.data.OrderStatus
import com.oldee.expert.data.OrderStatusIconFromString
import com.oldee.expert.data.OrderType
import com.oldee.expert.databinding.LayoutOrderItemOldBinding
import com.oldee.expert.databinding.LayoutOrderSubItemBinding
import com.oldee.expert.databinding.LayoutOrderSubItemReformBinding
import com.oldee.expert.retrofit.response.OrderListResponse.OrderData
import com.oldee.expert.retrofit.response.OrderListResponse.OrderSubData

class OrderListAdapter(
    val navController: NavController,
    val imageCallback: (ImageView, String) -> Unit,
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

    override fun submitList(list: MutableList<OrderData>?) {
        super.submitList(list?.let { ArrayList(it) })
    }

    override fun submitList(list: MutableList<OrderData>?, commitCallback: Runnable?) {
        super.submitList(list?.let { ArrayList(it) }, commitCallback)
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
                val status = d.orderStatusNm
//                val drawableId = getStatusIcon(d.orderStatusNm)
                binding.ivStatus.setImageResource(OrderStatusIconFromString[status]?:R.drawable.ic_cart)
                binding.ivStatus.setBackgroundResource(getStatusBackground(status))
                binding.tvStatus.text = d.orderStatusNm
                val colorId = getColor(context = binding.root.context, status)
                binding.tvStatus.setTextColor(colorId)
                binding.ivStatus.imageTintList = ColorStateList.valueOf(colorId)

                val list = data.orderList
                binding.llOrders.removeAllViews()
                if (list.isNotEmpty()) {
                    when(list[0].classCode){
                        OrderType.R.type->{
                            val inflater = LayoutInflater.from(binding.root.context)
                            list.forEach {
                                val subBinding = LayoutOrderSubItemReformBinding.inflate(
                                    inflater,
                                    null,
                                    false
                                )

                                binding.llOrders.addView(subBinding.root)
                                bindReformSubOrderView(subBinding, it, imageCallback)
                            }
                        }
                        else->{

                        }
                    }
//
//                    val inflater = LayoutInflater.from(binding.root.context)
//                    list.forEach {
//                        val subBinding = LayoutOrderSubItemBinding.inflate(
//                            inflater,
//                            binding.root as ViewGroup,
//                            false
//                        )
//
//                        bindSubOrderView(subBinding, it, imageCallback)
//                        binding.llOrders.removeAllViews()
//                        binding.llOrders.addView(subBinding.root)
//                    }
                }
//
//                val textColor = when (data.orderStatusCode) {
//                    OrderStatus.Working.value -> COLOR_BLUE
//                    OrderStatus.ShipmentComplete.value -> COLOR_RED
//                    else -> COLOR_GRAY
//                }
            }

            binding.root.setOnClickListener {
                val action =
                    OrderFragmentDirections.actionOrderFragmentToOrderDetailFragment(data.orderId)
                navController.navigate(action)
            }
        }

        fun bindReformSubOrderView(
            binding:LayoutOrderSubItemReformBinding,
            data:OrderSubData,
            imageCallback: (ImageView, String) -> Unit
        ){
            binding.sub = data

            imageCallback(binding.ivReform, data.imageName)
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

        fun getColor(context:Context, nm:String)=when(nm){
            "주문완료"->{
                ContextCompat.getColor(context, R.color.sky_6)
            }
            "수선중", "수선완료"->{
                ContextCompat.getColor(context, R.color.blush_7)
            }
            else->{
                ContextCompat.getColor(context, R.color.gray_7)
            }
        }

        fun getStatusBackground(nm:String) = when(nm){
            "주문완료"->{
                R.drawable.bg_circle_sky_2
            }
            "수선중", "수선완료"->{
                R.drawable.bg_circle_blush_2
            }
            else->{
                R.drawable.bg_circle_gray_2
            }
        }
    }

    class OrderListDiffCallback : DiffUtil.ItemCallback<OrderData>() {
        override fun areItemsTheSame(
            oldItem: OrderData,
            newItem: OrderData
        ) = oldItem.orderDetailId == newItem.orderDetailId

        override fun areContentsTheSame(
            oldItem: OrderData,
            newItem: OrderData
        ) = oldItem == newItem
    }
}