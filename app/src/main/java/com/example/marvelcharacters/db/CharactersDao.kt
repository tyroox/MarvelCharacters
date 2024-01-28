package com.example.marvelcharacters.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.marvelcharacters.model.Character


@Dao
interface CharactersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(character: Character): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(characters: List<Character>)

    @Query("SELECT * FROM characters")
    fun getAllCharacters(): LiveData<List<Character>>

    @Delete
    suspend fun deleteCharacter(character: Character)
}