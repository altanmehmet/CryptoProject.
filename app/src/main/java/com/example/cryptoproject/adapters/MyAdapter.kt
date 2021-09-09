
package com.example.cryptoproject.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.cryptoproject.model.MyDataItem
import com.example.cryptoproject.databinding.RowItemsBinding
import com.example.cryptoproject.helper.Utilities.getLastNCharsOfString
import com.example.cryptoproject.helper.Utilities.gorselIndir
import com.example.cryptoproject.helper.Utilities.placeholderYap
import android.net.Uri

import android.R

import com.bumptech.glide.load.model.StreamEncoder

import android.graphics.drawable.PictureDrawable
import com.example.cryptoproject.helper.Utilities.loadUrl

import java.io.InputStream




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
        val circularProgressDrawable = CircularProgressDrawable(holder.itemView.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
            holder.binding.imageMovie.loadUrl(userList.get(position).logo_url!!)

        }

    override fun getItemCount(): Int {
        return userList.size
    }
}
