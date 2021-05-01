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

class RequestDao {

    val db = FirebaseFirestore.getInstance()
    val postCollection = db.collection("Requests")
    val auth = Firebase.auth

    fun addRequest(name: String, phone: String, address: String, desc: String) {
        val currentUserId = auth.currentUser!!.uid
        GlobalScope.launch(Dispatchers.IO) {
            val userDao = UserDao()
            val user = userDao.getUserById(currentUserId).toString()
            val Request = Request(name, phone, address, desc, user)
            postCollection.document().set(Request)
        }
    }

    fun getPostById(id: String): Task<DocumentSnapshot> {
        return postCollection.document(id).get()
    }
}