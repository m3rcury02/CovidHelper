package com.example.covidhelper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.covidhelper.Daos.RequestDao
import com.example.covidhelper.Models.Request
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: RequestAdapter
    private lateinit var requestDao: RequestDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        requestDao = RequestDao()
        val requestCollection = requestDao.requestCollection
        val query = requestCollection.orderBy("createdBy", Query.Direction.DESCENDING)
        val recyclerViewOption = FirestoreRecyclerOptions.Builder<Request>().setQuery(query, Request::class.java).build()

        adapter = RequestAdapter(recyclerViewOption)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    fun request(view: View) {
        val intent = Intent(this, PostRequest::class.java)
        startActivity(intent)
    }
    fun donate(view: View) {
        val intent = Intent(this, Donate::class.java)
        startActivity(intent)
    }
}