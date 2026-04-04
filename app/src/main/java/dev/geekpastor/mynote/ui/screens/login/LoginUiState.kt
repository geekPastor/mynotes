package dev.geekpastor.mynote.ui.screens.login

sealed interface LoginUiState {

    object Idle : LoginUiState
    object Loading : LoginUiState
    object Success : LoginUiState
    data class Error(val message: String) : LoginUiState
}