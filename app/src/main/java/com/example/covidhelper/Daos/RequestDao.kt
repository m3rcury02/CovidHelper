package com.example.covidhelper.Daos

import com.example.covidhelper.Models.Request
import com.example.covidhelper.Models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope

class RequestDao {

    val db = FirebaseFirestore.getInstance()
    val requestCollection = db.collection("Requests")
    val auth = Firebase.auth

    fun addRequest(name: String, phone: String, address: String, desc: String) {
        val currentUserId = auth.currentUser!!.uid

        GlobalScope.launch(Dispatchers.IO) {
            val userDao = UserDao()
            val user = userDao.getUserById(currentUserId).await().toObject(User::class.java)!!
            val currentTime = System.currentTimeMillis()
            val request = Request(name, phone, address, desc, currentTime, user)
            requestCollection.document().set(request)
        }
    }

}