package dev.geekpastor.mynote.domain.usecases

import dev.geekpastor.mynote.domain.repository.UserRepository

class GetCurrentUserUseCase(
    private val repository: UserRepository
){
    suspend operator fun invoke() = repository.getCurrentUser()
}