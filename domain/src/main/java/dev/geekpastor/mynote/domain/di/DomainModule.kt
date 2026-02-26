package dev.geekpastor.mynote.domain.di

import dev.geekpastor.mynote.domain.usecases.CreateNoteUseCase
import dev.geekpastor.mynote.domain.usecases.DeleteNoteUseCase
import dev.geekpastor.mynote.domain.usecases.GetNoteDetailsUseCase
import dev.geekpastor.mynote.domain.usecases.GetNotesUseCase
import org.koin.dsl.module

val domainModule = module {

    factory { CreateNoteUseCase(get()) }
    factory { GetNotesUseCase(get()) }
    factory { GetNoteDetailsUseCase(get()) }
    factory { DeleteNoteUseCase(get()) }

}