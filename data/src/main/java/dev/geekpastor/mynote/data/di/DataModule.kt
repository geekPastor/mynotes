package dev.geekpastor.mynote.data.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
import org.koin.dsl.module

val dataModule = module {

    single { FirebaseFirestore.getInstance() }
    single { FirebaseAuth.getInstance() }

    single<NoteRepository> {
        NoteRepositoryImpl(get())
    }

    single<UserRepository> {
        UserRepositoryImpl(get(), get())
    }
}