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
    private lateinit var auth : FirebaseAuth
    lateinit var lineDataSet: LineDataSet
    lateinit var linelist: ArrayList<Entry>
    lateinit var lineData: LineData
    private var dollarText : Double = 0.0
    private var cryptoText : Double = 0.0
    private var result : Double = 0.0
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        binding.button.setOnClickListener {
            val intent = Intent(this, Comment::class.java)
            startActivity(intent)
        }
        val user = intent.extras!!.getSerializable("ist") as MyDataItem
        equalCurrency = user.currency.toString()
        cryptoText = user.price!!.toDouble()
        binding.desc.text = "Çevirmek istediğiniz para miktarını giriniz"
        binding.exchange.setOnClickListener {
            dollarText = binding.textView5.text.toString().toDouble()
            result = divide(dollarText,cryptoText)
            binding.textView6.text = result.toString() + " " + equalCurrency + " " + "alabilirsiniz."
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
            override fun onResponse(
                call: Call<List<MyDataGraphItemItem>?>,
                response: Response<List<MyDataGraphItemItem>?>
            ) {
                val responseBody = response.body()!!
                linelist = ArrayList()
                linelist.add(Entry(1f, responseBody[0].prices[0].toFloat()))
                linelist.add(Entry(2f, responseBody[0].prices[1].toFloat()))
                linelist.add(Entry(3f, responseBody[0].prices[2].toFloat()))
                linelist.add(Entry(4f, responseBody[0].prices[3].toFloat()))
                linelist.add(Entry(5f, responseBody[0].prices[4].toFloat()))
                linelist.add(Entry(6f, responseBody[0].prices[5].toFloat()))
                linelist.add(Entry(7f, responseBody[0].prices[6].toFloat()))
                linelist.add(Entry(8f, responseBody[0].prices[7].toFloat()))
                linelist.add(Entry(9f, responseBody[0].prices[8].toFloat()))
                linelist.add(Entry(10f, responseBody[0].prices[9].toFloat()))
                linelist.add(Entry(11f, responseBody[0].prices[10].toFloat()))
                linelist.add(Entry(12f, responseBody[0].prices[11].toFloat()))
                linelist.add(Entry(13f, responseBody[0].prices[12].toFloat()))
                linelist.add(Entry(14f, responseBody[0].prices[13].toFloat()))
                linelist.add(Entry(15f, responseBody[0].prices[14].toFloat()))
                linelist.add(Entry(16f, responseBody[0].prices[15].toFloat()))
                linelist.add(Entry(17f, responseBody[0].prices[16].toFloat()))
                linelist.add(Entry(18f, responseBody[0].prices[17].toFloat()))
                linelist.add(Entry(19f, responseBody[0].prices[18].toFloat()))
                linelist.add(Entry(20f, responseBody[0].prices[19].toFloat()))
                linelist.add(Entry(21f, responseBody[0].prices[20].toFloat()))
                linelist.add(Entry(22f, responseBody[0].prices[21].toFloat()))
                linelist.add(Entry(23f, responseBody[0].prices[22].toFloat()))
                linelist.add(Entry(24f, responseBody[0].prices[23].toFloat()))
                linelist.add(Entry(25f, responseBody[0].prices[24].toFloat()))
                linelist.add(Entry(26f, responseBody[0].prices[25].toFloat()))
                linelist.add(Entry(27f, responseBody[0].prices[26].toFloat()))
                linelist.add(Entry(28f, responseBody[0].prices[27].toFloat()))
                linelist.add(Entry(29f, responseBody[0].prices[28].toFloat()))
                linelist.add(Entry(30f, responseBody[0].prices[29].toFloat()))

                lineDataSet = LineDataSet(linelist, "Monthly Price")
                lineData = LineData(lineDataSet)
                line_chart.setNoDataText("Description that you want");
                line_chart.data = lineData
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
            else -> true
        }
    }
    fun switch(){
        binding.switchConvert.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.desc.text = "Çevirmek istediğiniz"+ " " + equalCurrency + " " + "miktarını giriniz"
                binding.exchange.setOnClickListener {
                    dollarText = binding.textView5.text.toString().toDouble()
                    result = dollarText * cryptoText
                    binding.textView6.text = result.toString() + "ödemeniz gerekli"
                }


            } else {
                binding.desc.text = "Çevirmek istediğiniz para miktarını giriniz"
                binding.exchange.setOnClickListener {
                    dollarText = binding.textView5.text.toString().toDouble()
                    result = divide(dollarText,cryptoText)
                    binding.textView6.text = result.toString() + " " + equalCurrency + " " + "alabilirsiniz."
                }

            }
        }
        }
    fun divide(number1 : Double,number2 : Double): Double {
        return number1 / number2
    }
    }

