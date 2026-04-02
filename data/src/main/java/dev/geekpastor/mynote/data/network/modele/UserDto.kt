package dev.geekpastor.mynote.data.network.modele

import java.util.Date


data class UserDto(
    val uid: String = "",
    val email: String = "",
    val name: String = "",
    val photoUrl: String = "",
    val createdAt: Date? = null,
    val updatedAt: Date? = null,
)
