package com.example.covidhelper.Daos

import com.example.covidhelper.Models.Request
import com.example.covidhelper.Models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RequestDao {

    val db = FirebaseFirestore.getInstance()
    val postCollection = db.collection("Posts")
    val auth = Firebase.auth

    fun addPost(text: String){
        val currentUserId = auth.currentUser!!.uid
        GlobalScope.launch(Dispatchers.IO) {
            val userDao = UserDao()
            val user = userDao.getUserById(currentUserId).await().toObject(User::class.java)!!
            val currentTime = System.currentTimeMillis()
            val post = Request(text, user, currentTime)
            postCollection.document().set(post)
        }
    }

    fun getPostById(id: String): Task<DocumentSnapshot>{
        return postCollection.document(id).get()
    }