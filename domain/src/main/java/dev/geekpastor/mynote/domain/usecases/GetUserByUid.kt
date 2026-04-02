package dev.geekpastor.mynote.domain.usecases

import dev.geekpastor.mynote.domain.repository.UserRepository

class GetUserByUid(
    private val repository: UserRepository
){
    suspend operator fun invoke(uid: String) = repository.getUserByUid(uid)
}