package com.example.cryptoproject.Activity

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.cryptoproject.*
import com.example.cryptoproject.Helper.ApiInterface
import com.example.cryptoproject.Model.MyDataGraphItemItem
import com.example.cryptoproject.Model.MyDataItem
import com.example.cryptoproject.databinding.ActivityDetailedBinding
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_detailed.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.collections.ArrayList


class DetailedActivity : AppCompatActivity() {
    val BASE_URL = "https://api.nomics.com/v1/"
    private lateinit var binding: ActivityDetailedBinding
    private var equalCurrency = " "
    private var convertCurrency = ""
    private lateinit var auth : FirebaseAuth
    lateinit var lineDataSet: LineDataSet
    lateinit var linelist: ArrayList<Entry>
    lateinit var lineData: LineData
    private var dollarText : Double = 0.0
    private var cryptoText : Double = 0.0
    private var result : Double = 0.0
    var stringBuild : String = ""
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        val user = intent.extras!!.getSerializable("ist") as MyDataItem
        equalCurrency = user.currency.toString()
        cryptoText = user.price!!.toDouble()
        binding.button.setOnClickListener {
            val intent = Intent(this, CommentActivity::class.java)
            startActivity(intent)
        }
        binding.desc.text = getString(R.string.donustur,equalCurrency)
        binding.exchange.setOnClickListener {
            dollarText = binding.textView5.text.toString().toDouble()
            result = divide(dollarText,cryptoText)
            binding.textView6.text = getString(R.string.donusturSonuc,result,equalCurrency)
        }
        switch()
        getMyGraphData()
        binding.currencyText.text = equalCurrency
        binding.textView1.text = "Current Price" + " " + user.price
        binding.textView2.text = "Dominance" + " " + user.market_cap_dominance
        binding.textView3.text = "Currency Rank" + " " + user.rank
        binding.textView4.text = "Market Cap" + " " + user.market_cap

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getMyGraphData() {
        val retrofitBuilder =
            Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(ApiInterface::class.java)
        var dateTime = LocalDateTime.now()
        var newwDate = dateTime.minusDays(30)
        var newDate = newwDate.atOffset(ZoneOffset.UTC)
        val retrofitData = retrofitBuilder.getDataGraph(equalCurrency,newDate.toString())

        retrofitData.enqueue(object : Callback<List<MyDataGraphItemItem>?> {
            override  fun onResponse(
                call: Call<List<MyDataGraphItemItem>?>,
                response: Response<List<MyDataGraphItemItem>?>
            ) {
                val responseBody = response.body()!!
                linelist = ArrayList()
                for (i in responseBody[0].prices.indices) {
                    linelist.add(Entry(i.toFloat(), responseBody[0].prices[i].toFloat()))
                }
                lineDataSet = LineDataSet(linelist, "Monthly Price")
                lineData = LineData(lineDataSet)
                line_chart.data = lineData
                line_chart.invalidate()
                lineDataSet.color = Color.BLACK
                lineDataSet.setColors(Color.BLACK)
                lineDataSet.valueTextColor = Color.BLACK
                lineDataSet.valueTextSize = 0f
                lineDataSet.setDrawFilled(true)
            }

            override fun onFailure(call: Call<List<MyDataGraphItemItem>?>, t: Throwable) {
                Log.d("MainActivity", "message" + t.message)
            }
        })

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
       this.menuInflater.inflate(R.menu.menu_item,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item?.itemId){
            R.id.sign_out -> {
                auth.signOut()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
                true
            }
            R.id.menu_refresh -> {
                this.recreate()
                true
            }
            else -> true
        }
    }
    fun switch(){
        binding.switchConvert.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.desc.text = getString(R.string.donusturTers,equalCurrency)
                binding.exchange.setOnClickListener {
                    dollarText = binding.textView5.text.toString().toDouble()
                    result = dollarText * cryptoText.toDouble()
                    binding.textView6.text = getString(R.string.donusturSonucTers,result)
                }


            } else {
                binding.desc.text = getString(R.string.donustur,equalCurrency)
                binding.exchange.setOnClickListener {
                    dollarText = binding.textView5.text.toString().toDouble()
                    result = divide(dollarText,cryptoText.toDouble())
                    binding.textView6.text = getString(R.string.donusturSonuc,result,equalCurrency)
                }

            }
        }
        }
    fun divide(number1 : Double,number2 : Double): Double {
        return number1 / number2
    }
    }

