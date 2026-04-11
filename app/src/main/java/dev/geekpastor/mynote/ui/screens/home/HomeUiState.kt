package dev.geekpastor.mynote.ui.screens.home

import dev.geekpastor.mynote.domain.model.Note

sealed interface HomeUiState{
    object Loading: HomeUiState
    object Empty: HomeUiState
    data class Success(val notes: List<Note>): HomeUiState
    data class Error(val throwable: Throwable): HomeUiState
}