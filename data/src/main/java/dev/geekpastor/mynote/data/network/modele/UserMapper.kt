package dev.geekpastor.mynote.data.network.modele

import dev.geekpastor.mynote.domain.model.User
import com.google.firebase.auth.FirebaseUser
import java.util.Date

fun UserDto.toDomain() = User(
    uid = uid, email = email, name = name, photoUrl = photoUrl, createdAt = createdAt, updatedAt = updatedAt
)

fun User.toDto() = UserDto(
    uid = uid, email = email, name = name, photoUrl = photoUrl, createdAt = createdAt, updatedAt = updatedAt
)

fun FirebaseUser.toDomainUser() = User(
    uid = uid,
    name = displayName!!,
    email = email!!,
    photoUrl = photoUrl.toString(),
    createdAt = Date(),
    updatedAt = Date(),
)