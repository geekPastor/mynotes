package dev.geekpastor.mynote.domain.repository

import dev.geekpastor.mynote.domain.model.Note
import kotlinx.coroutines.flow.Flow


interface NoteRepository{
    suspend fun createNote(note: Note)
    suspend fun deleteNote(noteId: String)
    suspend fun getNoteById(noteId: String): Note?
    suspend fun updateNote(note: Note)
    fun getAllNotes(): Flow<List<Note>>
}