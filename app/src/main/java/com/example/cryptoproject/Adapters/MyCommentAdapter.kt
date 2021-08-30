package com.example.cryptoproject.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoproject.Model.CommentDataClass
import com.example.cryptoproject.R

class MyCommentAdapter(private val userList : ArrayList<CommentDataClass>) :RecyclerView.Adapter<MyCommentAdapter.MyViewHolder2>() {

    class MyViewHolder2(itemView : View) : RecyclerView.ViewHolder(itemView) {
            val username :TextView = itemView.findViewById(R.id.tvfirstName)
            val comment :TextView = itemView.findViewById(R.id.tvlastName)
            val date : TextView = itemView.findViewById(R.id.tvage)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder2 {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_items_comment,parent,false)
        return MyViewHolder2(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder2, position: Int) {
        val currentitem = userList[position]
        holder.username.text = currentitem.username
        holder.comment.text = currentitem.comment
        holder.date.text = currentitem.date


    }

    override fun getItemCount(): Int {
        return userList.size
    }
}
