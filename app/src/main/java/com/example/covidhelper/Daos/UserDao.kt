package com.example.covidhelper.Daos

import com.example.covidhelper.Models.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*

class UserDao {
    val db = FirebaseFirestore.getInstance()
    val usersCollection = db.collection("Users")

    fun addUser(user: User?){
        user?.let {
            GlobalScope.launch(Dispatchers.IO) {
                usersCollection.document(user.uid).set(it)
            }
        }
    }

    fun getUserById(uId: String){

    }
}