package com.example.coordinatortablayout.RecyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coordinatortablayout.R

class RecyclerViewAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerViewViewHolder>() {
    var datas = mutableListOf<RecyclerData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder{
        val view = LayoutInflater.from(context).inflate(R.layout.rv_item,parent,false)
        return RecyclerViewViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder:RecyclerViewViewHolder, position: Int) {
        holder.bind(datas[position])
    }

}