package dev.geekpastor.mynote.domain.usecases

import dev.geekpastor.mynote.domain.repository.NoteRepository

class GetNotesUseCase(
    private val repository: NoteRepository
) {
    operator fun invoke() = repository.getNotes()
}