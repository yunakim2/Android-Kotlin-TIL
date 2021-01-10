package com.example.coordinatortablayout.RecyclerView

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coordinatortablayout.R
import kotlinx.android.synthetic.main.rv_item.view.*

class RecyclerViewViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    val tv = itemView.findViewById<TextView>(R.id.tv_recycler)

    fun bind(Data: RecyclerData)
    {
        tv.text = Data.string
    }
}