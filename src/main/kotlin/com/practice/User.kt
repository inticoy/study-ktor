package com.practice

@kotlinx.serialization.Serializable
data class User(
    val id: String? = null,
    val password: String? = null
)
