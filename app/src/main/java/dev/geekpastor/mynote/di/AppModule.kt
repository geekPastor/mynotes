package dev.geekpastor.mynote.di

import dev.geekpastor.mynote.MainViewModel
import dev.geekpastor.mynote.data.repository.NoteRepositoryImpl
import dev.geekpastor.mynote.data.repository.UserRepositoryImpl
import dev.geekpastor.mynote.domain.repository.NoteRepository
import dev.geekpastor.mynote.domain.repository.UserRepository
import dev.geekpastor.mynote.domain.usecases.CreateNoteUseCase
import dev.geekpastor.mynote.domain.usecases.DeleteNoteUseCase
import dev.geekpastor.mynote.domain.usecases.GetCurrentUserStreamUseCase
import dev.geekpastor.mynote.domain.usecases.GetCurrentUserUseCase
import dev.geekpastor.mynote.domain.usecases.GetNoteDetailsUseCase
import dev.geekpastor.mynote.domain.usecases.GetNotesUseCase
import dev.geekpastor.mynote.domain.usecases.GetUserByUid
import dev.geekpastor.mynote.domain.usecases.SignInWithCredentialUseCase
import dev.geekpastor.mynote.ui.NotesViewModel
import dev.geekpastor.mynote.ui.screens.login.LoginViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { NotesViewModel(get(), get(), get()) }
    viewModel { MainViewModel() }
    viewModel { LoginViewModel(get()) }
}