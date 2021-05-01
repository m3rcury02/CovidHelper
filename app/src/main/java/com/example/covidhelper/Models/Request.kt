package com.example.covidhelper.Models

data class Request(
    val name: String="",
    val phone: String="",
    val address: String="",
    val description: String = "",
    val createdAt: Long = 0L,
    val createdBy: User = User(),
)