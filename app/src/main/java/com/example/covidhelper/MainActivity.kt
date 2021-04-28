package com.example.covidhelper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val Reqbutton : Button = findViewById(R.id.button2)
        Reqbutton.setOnClickListener{
          Toast.makeText(this, "this is a request button !!", Toast.LENGTH_LONG).show()
        }
    }
}