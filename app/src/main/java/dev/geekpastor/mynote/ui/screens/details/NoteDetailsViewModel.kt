package dev.geekpastor.mynote.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.geekpastor.mynote.domain.repository.NoteRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteDetailViewModel(
    private val repository: NoteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<NoteDetailUiState>(NoteDetailUiState.Loading)
    val uiState: StateFlow<NoteDetailUiState> = _uiState.asStateFlow()

    // Init with the initial note
    fun loadNote(noteId: String) {
        viewModelScope.launch {
            _uiState.value = NoteDetailUiState.Loading

            try {
                val note = repository.getNoteById(noteId)
                    ?: throw Exception("Note introuvable")

                _uiState.value = NoteDetailUiState.Success(note)

            } catch (e: Exception) {
                _uiState.value = NoteDetailUiState.Error(e)
            }
        }
    }

    // Update title
    fun onTitleChange(newTitle: String) {
        val state = _uiState.value
        if (state is NoteDetailUiState.Success) {
            _uiState.value = state.copy(
                note = state.note.copy(title = newTitle)
            )
            autoSave()
        }
    }

    // Update content
    fun onContentChange(newContent: String) {
        val state = _uiState.value
        if (state is NoteDetailUiState.Success) {
            _uiState.value = state.copy(
                note = state.note.copy(content = newContent)
            )
            autoSave()
        }
    }

    // 💾 SAVE
    fun saveNote() {
        val state = _uiState.value
        if (state !is NoteDetailUiState.Success) return

        viewModelScope.launch {
            try {
                _uiState.value = state.copy(isSaving = true)

                repository.updateNote(state.note)

                _uiState.value = state.copy(isSaving = false)

            } catch (e: Exception) {
                _uiState.value = NoteDetailUiState.Error(e)
            }
        }
    }

    private var saveJob: Job? = null

    fun autoSave() {
        saveJob?.cancel()
        saveJob = viewModelScope.launch {
            delay(1000) // debounce
            saveNote()
        }
    }

    //Toggle Favorite
    fun toggleFavorite() {
        val state = _uiState.value
        if (state is NoteDetailUiState.Success) {
            val updatedNote = state.note.copy(
                isFavorite = !state.note.isFavorite
            )

            _uiState.value = state.copy(note = updatedNote)
            autoSave()
        }
    }

    // Delete Note
    fun deleteNote(onSuccess: () -> Unit) {
        val state = _uiState.value
        if (state !is NoteDetailUiState.Success) return

        viewModelScope.launch {
            try {
                repository.deleteNote(state.note.id)
                onSuccess()
            } catch (e: Exception) {
                _uiState.value = NoteDetailUiState.Error(e)
            }
        }
    }

    fun saveAndExit(onDone: () -> Unit) {
        viewModelScope.launch {
            saveNote()
            onDone()
        }
    }
}