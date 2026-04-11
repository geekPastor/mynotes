package dev.geekpastor.mynote.domain.di

import dev.geekpastor.mynote.domain.usecases.CreateNoteUseCase
import dev.geekpastor.mynote.domain.usecases.DeleteNoteUseCase
import dev.geekpastor.mynote.domain.usecases.GetCurrentUserStreamUseCase
import dev.geekpastor.mynote.domain.usecases.GetCurrentUserUseCase
import dev.geekpastor.mynote.domain.usecases.GetNoteDetailsUseCase
import dev.geekpastor.mynote.domain.usecases.GetNotesUseCase
import dev.geekpastor.mynote.domain.usecases.GetUserByUid
import dev.geekpastor.mynote.domain.usecases.SignInWithCredentialUseCase
import org.koin.dsl.module

val domainModule = module {

    factory { CreateNoteUseCase(get()) }
    factory { GetNotesUseCase(get()) }
    factory { GetNoteDetailsUseCase(get()) }
    factory { DeleteNoteUseCase(get()) }

    factory { GetUserByUid(get ()) }
    factory { GetCurrentUserUseCase(get()) }
    factory { GetCurrentUserStreamUseCase(get()) }
    factory { SignInWithCredentialUseCase(get ()) }
}