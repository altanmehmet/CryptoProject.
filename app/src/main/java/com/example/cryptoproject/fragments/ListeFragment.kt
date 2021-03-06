package com.example.cryptoproject.fragments

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.cryptoproject.activity.DetailedActivity
import com.example.cryptoproject.adapters.MyAdapter
import com.example.cryptoproject.helper.ApiInterface
import com.example.cryptoproject.model.MyDataItem
import com.example.cryptoproject.R
import com.example.cryptoproject.databinding.FragmentListeBinding
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_liste.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList


class ListeFragment : Fragment() {
    val BASE_URL = "https://api.nomics.com/v1/"
    private var myAdapter: MyAdapter? = null
    private var tempArrayList: ArrayList<MyDataItem> = arrayListOf()
    internal lateinit var api: ApiInterface
    internal var cp: CompositeDisposable? = null
    lateinit var linearLayoutManager: LinearLayoutManager
    private var _binding: FragmentListeBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    var liste: ArrayList<MyDataItem> = arrayListOf()

    /*private fun getMyData() {
        val retrofitBuilder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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
                myAdapter = MyAdapter(activity!!.baseContext, tempArrayList)
                binding.recyclerV.adapter = myAdapter
                myAdapter!!.setOnItemClickListener(object : MyAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        val intent = Intent(requireActivity(), DetailedActivity::class.java)
                        val dataI = MyDataItem(
                            circulating_supply = liste[position].circulating_supply,
                            max_supply = liste[position].max_supply,
                            price_date = liste[position].price_date,
                            price = liste[position].price,
                            first_order_book = liste[position].first_order_book,
                            first_candle = liste[position].first_candle,
                            market_cap_dominance = liste[position].market_cap_dominance,
                            market_cap = liste[position].market_cap,
                            first_trade = liste[position].first_trade,
                            high_timestamp = liste[position].high_timestamp,
                            high = liste[position].high,
                            logo_url = liste[position].logo_url,
                            num_exchanges = liste[position].num_exchanges,
                            num_pairs = liste[position].num_pairs,
                            num_pairs_unmapped = liste[position].num_pairs_unmapped,
                            symbol = liste[position].symbol,
                            id = liste[position].id,
                            status = liste[position].status,
                            currency = liste[position].currency,
                            price_timestamp = liste[position].price_timestamp,
                            rank_delta = liste[position].rank_delta,
                            name = liste[position].name,
                            rank = liste[position].rank
                        )
                        intent.putExtra("ist", dataI)
                        startActivity(intent)
                    }

                })
                //binding.recyclerV.adapter!!.notifyDataSetChanged()
                binding.progressBar.visibility = View.GONE

            }


            override fun onFailure(call: Call<List<MyDataItem>?>, t: Throwable) {
                Log.d("MainActivity", "message" + t.message)
            }
        })
    }
*/
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListeBinding.inflate(inflater, container, false)
        val view = binding.root
        cp = CompositeDisposable()
        linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerV.layoutManager = linearLayoutManager
        //getMyData()
        fetchData()
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        setHasOptionsMenu(true)
        auth = FirebaseAuth.getInstance()
        //tempArrayList = arrayListOf()

        return view
    }

    private fun fetchData() {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ApiInterface::class.java)
            cp!!.add(
            retrofitBuilder.getData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse)
        )
    }

    private fun handleResponse(userList: List<MyDataItem>) {

        liste = ArrayList(userList)
        tempArrayList.addAll(liste)
        myAdapter = MyAdapter(requireActivity().baseContext, liste)
        binding.recyclerV.adapter = myAdapter
        myAdapter!!.setOnItemClickListener(object : MyAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(requireActivity(), DetailedActivity::class.java)
                val dataI = MyDataItem(
                    circulating_supply = liste[position].circulating_supply,
                    max_supply = liste[position].max_supply,
                    price_date = liste[position].price_date,
                    price = liste[position].price,
                    first_order_book = liste[position].first_order_book,
                    first_candle = liste[position].first_candle,
                    market_cap_dominance = liste[position].market_cap_dominance,
                    market_cap = liste[position].market_cap,
                    first_trade = liste[position].first_trade,
                    high_timestamp = liste[position].high_timestamp,
                    high = liste[position].high,
                    logo_url = liste[position].logo_url,
                    num_exchanges = liste[position].num_exchanges,
                    num_pairs = liste[position].num_pairs,
                    num_pairs_unmapped = liste[position].num_pairs_unmapped,
                    symbol = liste[position].symbol,
                    id = liste[position].id,
                    status = liste[position].status,
                    currency = liste[position].currency,
                    price_timestamp = liste[position].price_timestamp,
                    rank_delta = liste[position].rank_delta,
                    name = liste[position].name,
                    rank = liste[position].rank
                )
                intent.putExtra("ist", dataI)
                startActivity(intent)
            }

        })
        binding.recyclerV.adapter!!.notifyDataSetChanged()
        binding.progressBar.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStop() {
        cp!!.clear()
        super.onStop()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().menuInflater.inflate(R.menu.menu_item, menu)
        val item = menu?.findItem(R.id.search_action)
        val searchView = item?.actionView as android.widget.SearchView
        searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
                TODO()
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {
                val searchText = newText!!.lowercase(Locale.getDefault())
                liste.clear()
                liste.addAll(tempArrayList)
                val filteredItems = liste.filterNot{ it.currency.toString().lowercase(Locale.getDefault()).contains(searchText) }
                liste.removeAll(filteredItems)
                binding.recyclerV.adapter!!.notifyDataSetChanged()
                return false
            }
        })
        return super.onCreateOptionsMenu(menu, inflater)
    }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {

            return when (item?.itemId) {
                R.id.sign_out -> {
                    auth.signOut()
                    val fragmentTransaction =
                        requireActivity().supportFragmentManager.beginTransaction()
                    val fragment = LoginFragment()
                    fragmentTransaction.replace(R.id.fragment_container, fragment)
                    fragmentTransaction.commit()
                    true
                }
                R.id.menu_refresh -> {
                    tempArrayList.clear()
                    binding.recyclerV.adapter!!.notifyDataSetChanged()
                    //getMyData()
                    fetchData()
                    true
                }
                else -> true
            }
        }
    }
