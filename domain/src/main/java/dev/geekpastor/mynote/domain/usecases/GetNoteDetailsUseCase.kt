package dev.geekpastor.mynote.domain.usecases

import dev.geekpastor.mynote.domain.repository.NoteRepository


class GetNoteDetailsUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id: String) =
        repository.getNoteById(id)
}