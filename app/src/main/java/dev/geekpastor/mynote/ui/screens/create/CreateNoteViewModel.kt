package dev.geekpastor.mynote.ui.screens.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.geekpastor.mynote.domain.model.Note
import dev.geekpastor.mynote.domain.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateNoteViewModel (
    private val noteRepository: NoteRepository
): ViewModel(){

    private val _uiState: MutableStateFlow<CreateNoteUiState> =
        MutableStateFlow(CreateNoteUiState.Initial)

    val uiState : StateFlow<CreateNoteUiState>  = _uiState.asStateFlow()

    fun createNote(note: Note){
        viewModelScope.launch {
            _uiState.emit(CreateNoteUiState.Loading)
            try {
                noteRepository.createNote(note)
                _uiState.emit(CreateNoteUiState.Success)
            }catch(e: Exception) {
                _uiState.emit(CreateNoteUiState.Error(message = "Une erreur est survenue"))
            }
        }
    }
}