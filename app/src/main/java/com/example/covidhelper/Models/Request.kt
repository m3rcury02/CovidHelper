package com.example.covidhelper.Models

data class Request(
    val Description: String = "",
    val name: String="",
    val phone: String="",
    val address: String="",
    val createdBy: User = User(),
    val createdAt: Long = 0L,
)