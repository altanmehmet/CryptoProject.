package com.example.cryptoproject

import com.example.cryptoproject.Model.MyDataGraphItemItem
import com.example.cryptoproject.Model.MyDataItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// /ticker?key=7ce49c49d0a6b5e38b62535e2924fa187452dc36&interval=1d,30d&convert=USD&per-page=100&page=1
//https://api.nomics.com/v1/currencies/ticker?key=7ce49c49d0a6b5e38b62535e2924fa187452dc36&ids=BTC&interval=1d,30d&convert=EUR&per-page=100&page=1
//7ce49c49d0a6b5e38b62535e2924fa187452dc36
interface ApiInterface {
    @GET("currencies/ticker?key=7ce49c49d0a6b5e38b62535e2924fa187452dc36&interval=1d,30d&convert=USD&per-page=100&page=1")
    fun getData(): Call<List<MyDataItem>>
    @GET("https://api.nomics.com/v1/currencies/sparkline?key=7ce49c49d0a6b5e38b62535e2924fa187452dc36&&&")
    fun getDataGraph(@Query(
        "ids"
        ) ids: String,@Query(
        "start"
    ) start: String
    ): Call<List<MyDataGraphItemItem>>//ids=&start=2018-04-14T00%3A00%3A00Zend=2018-05-14T00%3A00%3A00Z


}