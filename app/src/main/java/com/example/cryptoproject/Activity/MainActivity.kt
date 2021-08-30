package com.example.cryptoproject.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cryptoproject.Model.LoginFragment
import com.example.cryptoproject.R

class MainActivity : AppCompatActivity() {
    //private lateinit var binding: ActivityMainBinding
    val BASE_URL = "https://api.nomics.com/v1/"

    //lateinit var myAdapter : MyAdapter
    //lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val fragment = LoginFragment()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()



            /* binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = linearLayoutManager
        getMyData()
    }

    private fun getMyData() {
        val retrofitBuilder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)
        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<List<MyDataItem>?> {
            override fun onResponse(
                call: Call<List<MyDataItem>?>,
                response: Response<List<MyDataItem>?>
            ) {
                val responseBody = response.body()!!
                myAdapter = MyAdapter(baseContext, responseBody as MutableList<MyDataItem>)
                myAdapter.notifyDataSetChanged()
                binding.recyclerView.adapter = myAdapter
                myAdapter.setOnItemClickListener(object : MyAdapter.onItemClickListener {
                    override fun onItemClick(position: Int) {
                        val fragmentManager = supportFragmentManager
                        val fragmentTransaction = fragmentManager?.beginTransaction()
                        val firstFragment = DetailedFragment()
                        if (fragmentTransaction != null) {
                            fragmentTransaction.add(R.id.linear_lay, firstFragment).commit()}
                    }

                })

            }


            override fun onFailure(call: Call<List<MyDataItem>?>, t: Throwable) {
                d("MainActivity","message" + t.message)
            }
        })

        */
        }


    }

