package dev.geekpastor.mynote.domain.usecases

import dev.geekpastor.mynote.domain.model.Note
import dev.geekpastor.mynote.domain.repository.NoteRepository


class CreateNoteUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note) =
        repository.createNote(note)
}