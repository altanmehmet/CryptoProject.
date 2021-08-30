package com.example.cryptoproject.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoproject.Model.CommentDataClass
import com.example.cryptoproject.Adapters.MyCommentAdapter
import com.example.cryptoproject.R
import com.google.firebase.database.*

class Comment : AppCompatActivity() {
    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerView : RecyclerView
    private lateinit var userArrayList : ArrayList<CommentDataClass>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        userRecyclerView = findViewById(R.id.recyclerView1)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf<CommentDataClass>()
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
}