package com.example.marvelcharacters.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "characters"
)
data class Character(
    @PrimaryKey
    val id: Int = 0,
    val name: String = "",
    val comics: List<String> = listOf(),
    val series: List<String> = listOf(),
    val stories: List<String> = listOf(),
    val events: List<String> = listOf(),
    val thumbnail: String = "",
    val thumbnailExtension: String = ""
)
