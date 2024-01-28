package com.example.marvelcharacters.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.marvelcharacters.model.Character

@Entity
data class Result(
    val comics: Comics,
    val description: String,
    val events: Events,
    @PrimaryKey
    var id: Int,
    val modified: String,
    val name: String,
    val resourceURI: String,
    val series: Series,
    val stories: Stories,
    val thumbnail: Thumbnail,
    val urls: List<Url>
){
    fun newCharacter(): Character{
        return Character(
            name = name,
            id = id,
            comics = comics.items.map {
                it.name
            },
            series = series.items.map {
                it.name
            },
            stories = stories.items.map {
                it.name
            },
            events = events.items.map {
                it.name
            },
            thumbnail = thumbnail.path,
            thumbnailExtension = thumbnail.extension,
        )
    }
}