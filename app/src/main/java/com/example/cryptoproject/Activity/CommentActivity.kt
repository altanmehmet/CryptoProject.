package com.example.cryptoproject.Activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoproject.Model.CommentDataClass
import com.example.cryptoproject.Adapters.MyCommentAdapter
import com.example.cryptoproject.R
import com.example.cryptoproject.databinding.ActivityCommentBinding
import com.example.cryptoproject.databinding.ActivityDetailedBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_comment.*
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class CommentActivity : AppCompatActivity() {
    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerView : RecyclerView
    private lateinit var binding : ActivityCommentBinding
    private lateinit var userArrayList : ArrayList<CommentDataClass>
    private lateinit var auth : FirebaseAuth
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
        binding.imageClick.setOnClickListener {
            val comment = binding.commentText.text.toString()
            val username = binding.userNameText.text.toString()
            var dateTime = LocalDateTime.now()
            var newDate = dateTime.atOffset(ZoneOffset.UTC)
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
            val formatted = newDate.format(formatter)
            dbref = FirebaseDatabase.getInstance().getReference("Users")
            val User = CommentDataClass(username,comment,formatted.toString())
            dbref.child(randomUUID()).setValue(User).addOnFailureListener {
                Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
            }.addOnSuccessListener {
                binding.commentText.text.clear()
                binding.userNameText.text.clear()
            }
        }
        getUserData()
    }

    private fun getUserData() {
        dbref = FirebaseDatabase.getInstance().getReference("Users")

        dbref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){
                        val user = userSnapshot.getValue(CommentDataClass::class.java)

                        userArrayList.add(user!!)
                    }
                    userRecyclerView.adapter = MyCommentAdapter(userArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("hata")
            }

        })
    }
    private fun randomUUID(): String {
        val uuid = UUID.randomUUID()
        val uuidString = uuid.toString()
        return uuidString
    }
}