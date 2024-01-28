package com.example.marvelcharacters.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.marvelcharacters.model.Character



@Database(
    entities = [Character::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class CharactersDatabase: RoomDatabase() {

    abstract fun getCharacterDao () : CharactersDao

    companion object {
        @Volatile
        private var instance: CharactersDatabase? = null

        fun getInstance(context: Context): CharactersDatabase? {
            if (instance == null) {
                synchronized(CharactersDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CharactersDatabase::class.java, "characters.db"
                    ).build()
                }
            }
            return instance
        }
    }
}