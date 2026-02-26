package dev.geekpastor.mynote.data.network.modele

import dev.geekpastor.mynote.domain.model.Note

data class NoteDto(
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L
)