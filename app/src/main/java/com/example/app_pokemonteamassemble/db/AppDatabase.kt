package com.example.app_pokemonteamassemble.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.app_pokemonteamassemble.models.Pokemon

@Database(entities = [Pokemon::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract  fun getDao(): PokemonDao

    companion object{
        private var INSTANCE: AppDatabase?= null

        fun getInstance(context: Context): AppDatabase{
            if (INSTANCE == null) {
                INSTANCE = Room
                    .databaseBuilder(context, AppDatabase::class.java, "pokemonV3.db")
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }
    }
}