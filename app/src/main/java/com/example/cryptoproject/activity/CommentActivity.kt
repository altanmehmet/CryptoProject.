package com.example.cryptoproject.activity

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoproject.adapters.MyCommentAdapter
import com.example.cryptoproject.model.CommentDataClass
import com.example.cryptoproject.R
import com.example.cryptoproject.databinding.ActivityCommentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import kotlinx.android.synthetic.main.activity_comment.*
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.HashMap

class CommentActivity : AppCompatActivity() {
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var binding: ActivityCommentBinding
    private lateinit var userArrayList: ArrayList<CommentDataClass>
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var myAdapter: MyCommentAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        userRecyclerView = findViewById(R.id.recyclerView1)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)
        userArrayList = arrayListOf<CommentDataClass>()
        myAdapter = MyCommentAdapter(userArrayList)
        recyclerView1.adapter = myAdapter
        EventChangeListener()
        binding.imageClick.setOnClickListener {
            val comment = binding.commentText.text.toString()
            val username = binding.userNameText1.text.toString()
            var dateTime = LocalDateTime.now()
            var newDate = dateTime.atOffset(ZoneOffset.UTC)
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
            val formatted = newDate.format(formatter)
            saveFireStore(username,comment, formatted)
        }
    }

    private fun EventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("Users")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Log.e("Firestore error", error.message.toString())
                        return
                    }
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            userArrayList.add(dc.document.toObject(CommentDataClass::class.java))
                        }
                        myAdapter.notifyDataSetChanged()
                    }
                   /*var counter = true
                    for (i in userArrayList.indices) {
                        if (userArrayList[i].comment == null) {
                            counter = false
                        }
                    }
                    if(counter == true)
                        myAdapter.notifyDataSetChanged()*/
                }

            })
    }


    fun randomUUID(): String {
        val uuid = UUID.randomUUID()
        val uuidString = uuid.toString()
        return uuidString
    }

    fun saveFireStore(comment: String, date: String,username : String) {
        db = FirebaseFirestore.getInstance()
        val user: MutableMap<String, Any> = HashMap()
        user["username"] = username
        user["comment"] = comment
        user["date"] = date
        db.collection("Users")
            .add(user)
            .addOnSuccessListener {
                Toast.makeText(this,"record added",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this,"Failure",Toast.LENGTH_SHORT).show()
            }
    }
}