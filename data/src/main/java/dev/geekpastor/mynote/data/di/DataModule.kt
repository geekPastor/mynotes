package dev.geekpastor.mynote.data.di

import com.google.firebase.firestore.FirebaseFirestore
import dev.geekpastor.mynote.data.repository.NoteRepositoryImpl
import dev.geekpastor.mynote.domain.repository.NoteRepository
import org.koin.dsl.module

val dataModule = module {

    single { FirebaseFirestore.getInstance() }

    single<NoteRepository> {
        NoteRepositoryImpl(get())
    }
}