
package com.example.cryptoproject.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptoproject.Model.MyDataItem
import com.example.cryptoproject.R
import com.example.cryptoproject.databinding.RowItemsBinding

class MyAdapter(val context:Context, val userList : List<MyDataItem>): RecyclerView.Adapter<MyAdapter.MyViewHolder>(){
    private lateinit var mListener : onItemClickListener
        interface  onItemClickListener {
            fun onItemClick(position: Int)
        }
    fun setOnItemClickListener(listener : onItemClickListener){
        mListener = listener
    }
    inner class MyViewHolder(val binding: RowItemsBinding,listener : onItemClickListener): RecyclerView.ViewHolder(binding.root){
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

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.userId.text = userList[position].currency
        holder.binding.title.text = userList[position].price + " " + " $"
        Glide.with(context).load(userList[position].logo_url).placeholder(R.drawable.ic_launcher_background).into(holder.binding.imageMovie)
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}
