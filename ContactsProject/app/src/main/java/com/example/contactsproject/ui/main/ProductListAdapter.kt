package com.example.contactsproject.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsproject.Product
import com.example.contactsproject.R

class ProductListAdapter(private val productItemLayout: Int, listen : OnButtonClickListener) :
    RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {
    var listener : OnButtonClickListener = listen
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
            viewHolder.trash.setImageResource(R.drawable.ic_baseline_delete_24)
            viewHolder.trash.setOnClickListener(View.OnClickListener {
                val data: Product = productList!![i]
                listener.onButtonClick(data)
            })
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
    var trash : ImageButton

    init {
        itemTitle = itemView.findViewById(R.id.itemTitle)
        itemDetail = itemView.findViewById(R.id.itemDetail)
        trash = itemView.findViewById(R.id.trash)

    }
}

}
interface OnButtonClickListener{
    fun onButtonClick(data : Product);
}