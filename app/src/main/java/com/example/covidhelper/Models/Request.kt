package com.example.covidhelper.Models

data class Request(
    val name: String="",
    val phone: String="",
    val address: String="",
    val Description: String = "",
    val createdBy: User = User(),
)