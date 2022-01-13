package com.example.a2_recyclerviewcustommultitypeadapterkotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.lang.String

class CustomAdapter(
    val context: Context,
    val studentArrayList: ArrayList<Student>,
    val listener: OnBottomReachedListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        val TYPE_HEADER = 0
        val TYPE_AVAILABLE_YES = 1
        val TYPE_UNAVAILABLE_NO = 2
        val TYPE_FOOTER = 3
    }

    override fun getItemViewType(position: Int): Int {
        if (isHeader(position)) return TYPE_HEADER
        else if (isFooter(position)) return TYPE_FOOTER
        else if (studentArrayList[position].available) {
            return TYPE_AVAILABLE_YES
        } else {
            return TYPE_UNAVAILABLE_NO
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_HEADER) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_header, parent, false)
            return HeaderViewHolder(view)
        }

        if (viewType == TYPE_FOOTER) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_footer, parent, false)
            return FooterViewHolder(view)
        }

        if (viewType == TYPE_AVAILABLE_YES) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
            return CustomViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.unavailable_item, parent, false)
            return CustomViewHolderUnavailable(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (position == studentArrayList.size - 1) {
            listener.onBottomReached(position)
        }

        if (holder is HeaderViewHolder)
            holder.tv_item_header.text = "This is the Header!"

        if (holder is FooterViewHolder)
            holder.tv_item_footer.text = "This is the Footer"


        if (holder is CustomViewHolder) {
            holder.tvOrder.text = "$position"
            holder.tvFullName.text = studentArrayList[position].fullName
            holder.tvAge.text = String.valueOf(studentArrayList[position].age)
        }

        if (holder is CustomViewHolderUnavailable) {
            holder.tvOrder.text = (position).toString()
            holder.tvFullName.text = studentArrayList[position].fullName
            holder.tvAge.text = String.valueOf(studentArrayList[position].age)
        }

    }

    override fun getItemCount(): Int {
        return studentArrayList.size
    }

    class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvOrder = view.findViewById<TextView>(R.id.order_no_item)
        val tvFullName = view.findViewById<TextView>(R.id.tv_fullname_item)
        val tvAge = view.findViewById<TextView>(R.id.tv_age_item)
    }

    class CustomViewHolderUnavailable(view: View) : RecyclerView.ViewHolder(view) {
        val tvOrder = view.findViewById<TextView>(R.id.order_no_item_unavailable)
        val tvFullName = view.findViewById<TextView>(R.id.tv_fullname_item_unavailable)
        val tvAge = view.findViewById<TextView>(R.id.tv_age_item_unavailable)
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_item_header = view.findViewById<TextView>(R.id.item_header_claim)
    }

    class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_item_footer = view.findViewById<TextView>(R.id.item_footer_claim)
    }

    fun isHeader(position: Int): Boolean {
        return (position == 0)
    }

    fun isFooter(position: Int): Boolean {
        return (position == studentArrayList.size - 1)
    }
}
