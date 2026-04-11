package dev.geekpastor.mynote.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import dev.geekpastor.mynote.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private  val userRepository: UserRepository
): ViewModel(){
    private  val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState.Idle)

    val uiState: StateFlow<LoginUiState>
        get() = _uiState

    fun signInWithGoogle(
        credential: AuthCredential
    ){
        viewModelScope.launch {
            _uiState.emit(LoginUiState.Loading)
            try {
                userRepository.signInWithCredential(credential = credential)
                _uiState.emit(LoginUiState.Success)
            }catch (e: Exception){
                _uiState.emit(LoginUiState.Error(e.message ?: "Une erreur est survenue"))
            }
        }
    }

    fun startAuthFlow(){
        _uiState.value = LoginUiState.Loading
    }
}