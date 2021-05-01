package com.example.covidhelper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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