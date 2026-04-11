package dev.geekpastor.mynote.data.network.modele

import dev.geekpastor.mynote.domain.model.Note

fun NoteDto.toDomain() = Note(
    id = id, userId = userId, title = title, content = content, isArchived = isArchived, isFavorite = isFavorite, createdAt = createdAt, updatedAt = updatedAt,
)

fun Note.toDto() = NoteDto(
    id = id, userId = userId, title =  title, content = content, isFavorite = isFavorite, isArchived = isArchived, createdAt = createdAt, updatedAt = updatedAt
)