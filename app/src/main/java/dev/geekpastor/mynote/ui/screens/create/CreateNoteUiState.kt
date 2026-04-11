package dev.geekpastor.mynote.ui.screens.create

sealed interface CreateNoteUiState{
    object Initial: CreateNoteUiState
    object Loading: CreateNoteUiState
    object Success: CreateNoteUiState
    data class Error(val message: String): CreateNoteUiState
}