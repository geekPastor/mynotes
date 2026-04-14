package dev.geekpastor.mynote.ui.screens.details

import dev.geekpastor.mynote.domain.model.Note

sealed interface NoteDetailUiState {

    object Loading : NoteDetailUiState

    data class Success(
        val note: Note,
        val isSaving: Boolean = false
    ) : NoteDetailUiState

    data class Error(
        val throwable: Throwable
    ) : NoteDetailUiState
}