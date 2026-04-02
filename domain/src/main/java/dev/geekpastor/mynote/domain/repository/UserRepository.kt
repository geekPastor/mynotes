package dev.geekpastor.mynote.domain.repository

import com.google.firebase.auth.AuthCredential
import dev.geekpastor.mynote.domain.model.User
import kotlinx.coroutines.flow.Flow

interface  UserRepository{
    suspend fun signInWithCredential(credential: AuthCredential)
    suspend fun getCurrentUser() : User
    fun getCurrentUserStream() : Flow<User>
    suspend fun getUserByUid(userUid: String) : User?
}