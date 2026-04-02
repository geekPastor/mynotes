package dev.geekpastor.mynote.domain.model

import java.util.Date


data class User(
    val uid: String,
    val email: String,
    val name: String,
    val photoUrl: String,
    val createdAt: Date?,
    val updatedAt: Date?
)