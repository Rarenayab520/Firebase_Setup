package com.codingempire.firebase_setup

import java.io.Serializable

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val createdAt: Long = 0L
): Serializable
