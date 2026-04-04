package dev.geekpastor.mynote.di

import dev.geekpastor.mynote.MainViewModel
import dev.geekpastor.mynote.ui.NotesViewModel
import dev.geekpastor.mynote.ui.screens.login.LoginViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { NotesViewModel(get(), get(), get()) }
    viewModel { MainViewModel() }
    viewModel { LoginViewModel(get()) }
}