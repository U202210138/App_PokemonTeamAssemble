package com.example.app_pokemonteamassemble.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pokemon(
    @PrimaryKey
    val id: Int? = null,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "team_id")
    val teamId: Int
)