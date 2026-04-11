package dev.geekpastor.mynote.domain.usecases

import dev.geekpastor.mynote.domain.model.Note
import dev.geekpastor.mynote.domain.repository.NoteRepository

class UpdateNoteUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note) = repository.updateNote(note)
}