package dev.geekpastor.mynote.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import dev.geekpastor.mynote.data.network.modele.NoteDto
import dev.geekpastor.mynote.data.network.modele.toDomain
import dev.geekpastor.mynote.data.network.modele.toDto
import dev.geekpastor.mynote.domain.model.Note
import dev.geekpastor.mynote.domain.repository.NoteRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await


class NoteRepositoryImpl(
    private val firestore: FirebaseFirestore
) : NoteRepository {

    private val notesCollection =
        firestore.collection("notes")

    override suspend fun createNote(note: Note) {
        notesCollection
            .document(note.id)
            .set(note.toDto())
            .await()
    }

    override suspend fun deleteNote(noteId: String) {
        notesCollection
            .document(noteId)
            .delete()
            .await()
    }

    override suspend fun getNoteById(noteId: String): Note? {
        val snapshot =
            notesCollection.document(noteId).get().await()

        return snapshot.toObject(NoteDto::class.java)
            ?.toDomain()
    }

    override fun getNotes(): Flow<List<Note>> =
        callbackFlow {

            val listener =
                notesCollection.addSnapshotListener { value, _ ->
                    val notes = value?.documents
                        ?.mapNotNull {
                            it.toObject(NoteDto::class.java)
                                ?.toDomain()
                        } ?: emptyList()

                    trySend(notes)
                }

            awaitClose { listener.remove() }
        }
}