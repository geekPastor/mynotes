package dev.geekpastor.mynote.domain.model

data class Note(
    val id: String = "",
    val userId: String = "",

    val title: String,
    val content: String,

    val isFavorite : Boolean = false,
    val isArchived: Boolean = false,

    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)