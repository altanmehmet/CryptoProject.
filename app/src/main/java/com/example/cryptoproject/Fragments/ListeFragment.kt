package com.example.cryptoproject.Fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptoproject.Activity.DetailedActivity
import com.example.cryptoproject.Adapters.MyAdapter
import com.example.cryptoproject.ApiInterface
import com.example.cryptoproject.Model.MyDataItem
import com.example.cryptoproject.R
import com.example.cryptoproject.databinding.FragmentListeBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_liste.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList


class ListeFragment : Fragment() {
    val BASE_URL = "https://api.nomics.com/v1/"
    lateinit var myAdapter: MyAdapter
    private lateinit var tempArrayList : ArrayList<MyDataItem>
    lateinit var linearLayoutManager: LinearLayoutManager
    private var _binding: FragmentListeBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth : FirebaseAuth
    var liste : List<MyDataItem> = listOf()
    private fun getMyData() {
        val retrofitBuilder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)
        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<List<MyDataItem>?> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<List<MyDataItem>?>,
                response: Response<List<MyDataItem>?>
            ) {
                val responseBody = response.body()!!
                liste = responseBody
                tempArrayList.addAll(liste)
                myAdapter = MyAdapter(activity!!.baseContext,tempArrayList)
                binding.recyclerV.adapter = myAdapter
                myAdapter.setOnItemClickListener(object : MyAdapter.OnItemClickListener{
                    override fun onItemClick(position: Int) {
                        val intent = Intent(requireActivity(), DetailedActivity::class.java)
                        val dataI = MyDataItem(circulating_supply = liste[position].circulating_supply
                        ,max_supply = liste[position].max_supply
                        ,price_date = liste[position].price_date
                        ,price = liste[position].price
                        ,first_order_book = liste[position].first_order_book
                        ,first_candle = liste[position].first_candle
                        ,market_cap_dominance = liste[position].market_cap_dominance
                        ,market_cap = liste[position].market_cap
                        ,first_trade = liste[position].first_trade
                        ,high_timestamp = liste[position].high_timestamp
                        ,high = liste[position].high
                        ,logo_url = liste[position].logo_url
                        ,num_exchanges = liste[position].num_exchanges
                        ,num_pairs = liste[position].num_pairs
                        ,num_pairs_unmapped = liste[position].num_pairs_unmapped
                        ,symbol = liste[position].symbol
                        ,id = liste[position].id
                        ,status = liste[position].status
                        ,currency = liste[position].currency
                        ,price_timestamp = liste[position].price_timestamp
                        ,rank_delta = liste[position].rank_delta
                        ,name = liste[position].name
                        ,rank = liste[position].rank)
                        //Log.d("MC2", "posted list: $liste")
                        intent.putExtra("ist", dataI)
                        startActivity(intent)
                    }

                })
                binding.recyclerV.adapter!!.notifyDataSetChanged()
            }


            override fun onFailure(call: Call<List<MyDataItem>?>, t: Throwable) {
                Log.d("MainActivity", "message" + t.message)
            }
        })
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListeBinding.inflate(inflater, container, false)
        val view = binding.root
        setHasOptionsMenu(true)
        auth = FirebaseAuth.getInstance()
        tempArrayList = arrayListOf()
        linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerV.layoutManager = linearLayoutManager
        getMyData()

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().menuInflater.inflate(R.menu.menu_item,menu)
        val item = menu?.findItem(R.id.search_action)
        val searchView = item?.actionView as android.widget.SearchView
        searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO()
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {
                tempArrayList.clear()
                val searchText = newText!!.lowercase(Locale.getDefault())
                if(searchText.isNotEmpty()){
                    liste.forEach {
                        if(it.currency.toString().lowercase(Locale.getDefault()).contains(searchText)){
                            tempArrayList.add(it)
                    }
                    }
                    binding.recyclerV.adapter!!.notifyDataSetChanged()
                }
                else {
                    tempArrayList.clear()
                    tempArrayList.addAll(liste)
                    binding.recyclerV.adapter!!.notifyDataSetChanged()
                }
                return false
            }
        })
        return super.onCreateOptionsMenu(menu,inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item?.itemId){
            R.id.sign_out -> {
                auth.signOut()
                val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
                val fragment = LoginFragment()
                fragmentTransaction.replace(R.id.fragment_container, fragment)
                fragmentTransaction.commit()
                true
            }
            R.id.menu_refresh -> {
                tempArrayList.clear()
                binding.recyclerV.adapter!!.notifyDataSetChanged()
                getMyData()
                true
            }
            else -> true
        }
    }
}