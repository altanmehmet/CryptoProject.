package com.example.cryptoproject.helper

import android.content.Context
import android.util.Log
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cryptoproject.R

object Utilities {
    fun ImageView.gorselIndir(url: String?, placeholder: CircularProgressDrawable){

        val options = RequestOptions().placeholder(placeholder).error(R.drawable.ic_launcher_foreground)
        Log.wtf("tag",url)
        Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)

    }

    fun placeholderYap(context: Context) : CircularProgressDrawable{
        return CircularProgressDrawable(context).apply {
            strokeWidth = 8f
            centerRadius = 40f
            start()
        }
    }
}