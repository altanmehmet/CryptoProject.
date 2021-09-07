
package com.example.cryptoproject.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cryptoproject.Model.MyDataItem
import com.example.cryptoproject.R
import com.example.cryptoproject.databinding.RowItemsBinding

class MyAdapter(var context:Context, private val userList : List<MyDataItem>): RecyclerView.Adapter<MyAdapter.MyViewHolder>(){
    private lateinit var mListener : OnItemClickListener
        interface  OnItemClickListener {
            fun onItemClick(position: Int)
        }
    fun setOnItemClickListener(listener : OnItemClickListener){
        mListener = listener
    }
    inner class MyViewHolder(val binding: RowItemsBinding,listener : OnItemClickListener): RecyclerView.ViewHolder(binding.root){
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            RowItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ,mListener)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.userId.text = userList[position].currency
        holder.binding.title.text = userList[position].price + " " + " $"
        Glide.with(holder.itemView.context)
            .load(userList[position].logo_url)
            .into(holder.binding.imageMovie)
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}
