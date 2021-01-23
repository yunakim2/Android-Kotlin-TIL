package com.example.recyclerview_ex.recyclerview_multi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recyclerview_ex.R

class MultiviewAdpater(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var datas = mutableListOf<MultiData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {
        val view : View?
        return when(viewType) {
            multi_type1 -> {
                view = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_recycler_ex,
                    parent,
                    false
                )
                MultiViewHolder1(view)
            }
            multi_type2 -> {
                view = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_recycler_multi1,
                    parent,
                    false
                )
                MultiViewHolder2(view)
            }
            else -> {
                view = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_recycler_multi2,
                    parent,
                    false
                )
                MultiViewHolder3(view)
            }
        }
    }
    override fun getItemCount(): Int = datas.size

    override fun getItemViewType(position: Int): Int {
        return datas[position].type
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(datas[position].type) {
            multi_type1 -> {
                (holder as MultiViewHolder1).bind(datas[position])
                holder.setIsRecyclable(false)
            }
            multi_type2 -> {
                (holder as MultiViewHolder2).bind(datas[position])
                holder.setIsRecyclable(false)
            }
            else -> {
                (holder as MultiViewHolder3).bind(datas[position])
                holder.setIsRecyclable(false)
            }
        }
    }

    inner class MultiViewHolder1(view: View) : RecyclerView.ViewHolder(view) {

        private val txtName: TextView = view.findViewById(R.id.tv_rv_name)
        private val txtAge: TextView = view.findViewById(R.id.tv_rv_age)
        private val imgProfile: ImageView = view.findViewById(R.id.img_rv_photo)

        fun bind(item: MultiData) {
            txtName.text = item.name
            txtAge.text = item.age.toString()
            Glide.with(itemView).load(item.image).into(imgProfile)

        }
    }
    inner class MultiViewHolder2(view: View) : RecyclerView.ViewHolder(view) {

        private val txtName: TextView = view.findViewById(R.id.tv_rv_name)
        private val txtAge: TextView = view.findViewById(R.id.tv_rv_age)
        private val imgProfile: ImageView = view.findViewById(R.id.img_rv_photo)

        fun bind(item: MultiData) {
            txtName.text = item.name
            txtAge.text = item.age.toString()
            Glide.with(itemView).load(item.image).into(imgProfile)

        }
    }

    inner class MultiViewHolder3(view: View) : RecyclerView.ViewHolder(view) {

        private val txtName: TextView = view.findViewById(R.id.tv_rv_name)
        private val imgProfile: ImageView = view.findViewById(R.id.img_rv_photo)

        fun bind(item: MultiData) {
            txtName.text = item.name
            Glide.with(itemView).load(item.image).into(imgProfile)

        }
    }
}