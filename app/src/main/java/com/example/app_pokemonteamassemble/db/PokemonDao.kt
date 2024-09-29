package com.example.app_pokemonteamassemble.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.app_pokemonteamassemble.models.Pokemon

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pokemon: List<Pokemon>)

    @Query("SELECT * FROM pokemon WHERE team_id = :teamId")
    fun getTeam(teamId: Int): List<Pokemon>

    @Query("SELECT DISTINCT team_id FROM pokemon")
    fun getAllTeamIds(): List<Int>
}