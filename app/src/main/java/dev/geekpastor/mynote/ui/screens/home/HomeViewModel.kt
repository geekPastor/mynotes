package dev.geekpastor.mynote.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.geekpastor.mynote.domain.repository.NoteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    noteRepository: NoteRepository
): ViewModel(){

    val uiState: StateFlow<HomeUiState> = noteRepository.getAllNotes()
        .map { notes->
            HomeUiState.Success(notes = notes)
        }
        .catch<HomeUiState> {
            emit(HomeUiState.Error(it))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState.Loading
        )

}