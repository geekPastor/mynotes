package dev.geekpastor.mynote.domain.usecases

import com.google.firebase.auth.AuthCredential
import dev.geekpastor.mynote.domain.repository.UserRepository

class SignInWithCredentialUseCase(
    private val repository: UserRepository
){
    suspend operator fun invoke(credential: AuthCredential) = repository.signInWithCredential(credential)
}