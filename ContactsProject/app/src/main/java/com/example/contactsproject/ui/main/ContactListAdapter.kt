package com.example.contactsproject.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsproject.Contact
import com.example.contactsproject.R

class ContactListAdapter(listen: OnButtonClickListener) :
    RecyclerView.Adapter<ContactListAdapter.ViewHolder>() {
    private var listener : OnButtonClickListener = listen
    private var contactList: List<Contact>? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.card_layout, viewGroup, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        contactList.let {

            viewHolder.itemTitle.text = it!![i].contactName
            viewHolder.itemDetail.text = it[i].phoneNumber
            viewHolder.trash.setImageResource(R.drawable.ic_baseline_delete_24)
            viewHolder.trash.setOnClickListener {
                val data: Contact = contactList!![i]
                listener.onButtonClick(data)
            }
        }
    }

    fun setContactList(contacts: List<Contact>) {
        contactList = contacts
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (contactList == null) 0 else contactList!!.size
    }

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
    fun onButtonClick(data : Contact)
}