package dev.geekpastor.mynote.data.network.modele

import dev.geekpastor.mynote.domain.model.Note

fun NoteDto.toDomain() = Note(
    id, title, content, createdAt, updatedAt
)

fun Note.toDto() = NoteDto(
    id, title, content, createdAt, updatedAt
)