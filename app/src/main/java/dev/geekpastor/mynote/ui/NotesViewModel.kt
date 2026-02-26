package dev.geekpastor.mynote.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.geekpastor.mynote.domain.model.Note
import dev.geekpastor.mynote.domain.usecases.CreateNoteUseCase
import dev.geekpastor.mynote.domain.usecases.DeleteNoteUseCase
import dev.geekpastor.mynote.domain.usecases.GetNotesUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID


class NotesViewModel(
    private val createNote: CreateNoteUseCase,
    private val getNotes: GetNotesUseCase,
    private val deleteNote: DeleteNoteUseCase
) : ViewModel() {

    val notes = getNotes()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList()
        )

    fun addNote(title: String, content: String) {
        viewModelScope.launch {

            val now = System.currentTimeMillis()

            createNote(
                Note(
                    id = UUID.randomUUID().toString(),
                    title = title,
                    content = content,
                    createdAt = now,
                    updatedAt = now
                )
            )
        }
    }

    fun removeNote(id: String) {
        viewModelScope.launch {
            deleteNote(id)
        }
    }
}