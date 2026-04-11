package dev.geekpastor.mynote.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import dev.geekpastor.mynote.domain.model.Note
import dev.geekpastor.mynote.domain.repository.NoteRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class NoteRepositoryImpl(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : NoteRepository {

    private fun getUserId(): String {
        return firebaseAuth.currentUser?.uid
            ?: throw Exception("User not authenticated")
    }

    private fun notesRef() =
        firestore.collection("users")
            .document(getUserId())
            .collection("notes")

    // REALTIME NOTE FLOW
    override fun getAllNotes(): Flow<List<Note>> = callbackFlow {
        val listener: ListenerRegistration = notesRef()
            .orderBy("updatedAt")
            .addSnapshotListener { snapshot, error ->

                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val notes = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(Note::class.java)?.copy(id = doc.id)
                } ?: emptyList()

                trySend(notes)
            }

        awaitClose { listener.remove() }
    }

    // GET ONE NOTE
    override suspend fun getNoteById(noteId: String): Note? {
        val doc = notesRef().document(noteId).get().await()
        return doc.toObject(Note::class.java)?.copy(id = doc.id)
    }

    // CREATE NOTE
    override suspend fun createNote(note: Note) {
        val docRef = notesRef().document()

        val newNote = note.copy(
            id = docRef.id,
            userId = getUserId(),
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )

        docRef.set(newNote).await()
    }

    // UPDATE NOTE
    override suspend fun updateNote(note: Note) {
        val updatedNote = note.copy(
            updatedAt = System.currentTimeMillis()
        )

        notesRef()
            .document(note.id)
            .set(updatedNote)
            .await()
    }

    // DELETE NOTE
    override suspend fun deleteNote(noteId: String) {
        notesRef()
            .document(noteId)
            .delete()
            .await()
    }
}