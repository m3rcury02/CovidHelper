package com.example.covidhelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.covidhelper.Daos.RequestDao
import kotlinx.android.synthetic.main.activity_post_request.*

class PostRequest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_request)
    }


    fun postRequest(view: View) {
        val name = name.text.toString().trim()
        val phone = phone.text.toString().trim()
        val address = address.text.toString().trim()
        val desc = desc.text.toString().trim()
        val post = RequestDao()
        post.addRequest(name, phone, address, desc)
        Toast.makeText(this, "successfully added..", Toast.LENGTH_SHORT).show()
        finish()
    }
}