package dev.geekpastor.mynote

import dev.geekpastor.mynote.ui.NotesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel {
        NotesViewModel(
            get(),
            get(),
            get()
        )
    }

}