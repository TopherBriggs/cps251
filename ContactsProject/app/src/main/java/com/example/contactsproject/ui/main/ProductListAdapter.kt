package com.example.contactsproject.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsproject.Product
import com.example.contactsproject.R

class ProductListAdapter(private val productItemLayout: Int) :
    RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    private var productList: List<Product>? = null

//    override fun onBindViewHolder(holder: ViewHolder, listPosition: Int) {
//        val item = holder.item
//        productList.let {
//            item.text = it!![listPosition].contactName
//        }
//    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.card_layout, viewGroup, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        productList.let {

            viewHolder.itemTitle.text = it!![i].contactName
            viewHolder.itemDetail.text = it[i].phoneNumber
        }

    }

    fun setProductList(products: List<Product>) {
        productList = products
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (productList == null) 0 else productList!!.size
    }

//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        var item: TextView = itemView.findViewById(R.id.product_row)
//    }
inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    var itemTitle: TextView
    var itemDetail: TextView

    init {
        itemTitle = itemView.findViewById(R.id.itemTitle)
        itemDetail = itemView.findViewById(R.id.itemDetail)

    }
}
}