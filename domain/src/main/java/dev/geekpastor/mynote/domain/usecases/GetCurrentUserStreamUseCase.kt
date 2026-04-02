package dev.geekpastor.mynote.domain.usecases

import dev.geekpastor.mynote.domain.repository.UserRepository

class GetCurrentUserStreamUseCase(
    private val repository: UserRepository
){
    operator fun invoke() = repository.getCurrentUserStream()
}