package dev.geekpastor.mynote.data.repository

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dev.geekpastor.mynote.data.network.modele.toDomainUser
import dev.geekpastor.mynote.domain.model.User
import dev.geekpastor.mynote.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
): UserRepository{

    private suspend fun saveUserToFirestore(firebaseUser: FirebaseUser?){
        withContext(Dispatchers.IO){
            val user = firebaseUser?.toDomainUser()
            if (user != null) {
                firestore.collection("USERS").document(user.uid).set(user)
                    .await()
            }
        }
    }


    override suspend fun signInWithCredential(credential: AuthCredential) {
        withContext(Dispatchers.IO){
            try {
                val user = firebaseAuth.signInWithCredential(credential).await().user
                saveUserToFirestore(user)
            }catch (e: Exception){
                throw e
            }
        }
    }

    override suspend fun getCurrentUser(): User {
        TODO("Not yet implemented")
    }

    override fun getCurrentUserStream(): Flow<User> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserByUid(userUid: String): User? {
        TODO("Not yet implemented")
    }
}