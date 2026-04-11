package dev.geekpastor.mynote.data.network.modele

import dev.geekpastor.mynote.domain.model.Note

data class NoteDto(
    val id: String = "",
    val userId: String = "",

    val title: String = "",
    val content: String = "",

    val isFavorite : Boolean = false,
    val isArchived: Boolean = false,

    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)