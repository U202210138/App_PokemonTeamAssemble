package com.example.app_pokemonteamassemble.communication

import com.example.app_pokemonteamassemble.models.Pokemon

class PokemonResponse(
    var name: String,
    var sprites: Sprites
) {
    fun toPokemon(teamId: Int): Pokemon {
        return Pokemon(
            name = name,
            url = sprites.front_default,
            teamId = teamId
        )
    }
}

data class Sprites(
    var front_default: String
)