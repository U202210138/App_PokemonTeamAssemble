// app/src/main/java/com/example/app_pokemonteamassemble/activities/AssembleActivity.kt
package com.example.app_pokemonteamassemble.activities

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_pokemonteamassemble.R
import com.example.app_pokemonteamassemble.adapters.PokemonAdapter
import com.example.app_pokemonteamassemble.communication.PokemonResponse
import com.example.app_pokemonteamassemble.db.AppDatabase
import com.example.app_pokemonteamassemble.models.Pokemon
import com.example.app_pokemonteamassemble.network.PokeApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class AssembleActivity : AppCompatActivity(), PokemonAdapter.OnItemClickListener {
    private lateinit var btRandom: Button
    private lateinit var etTeamNumber: EditText
    private lateinit var rvPokemon: RecyclerView
    private var pokemonList: List<Pokemon> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assemble)
        setSupportActionBar(findViewById(R.id.toolbar))

        btRandom = findViewById(R.id.btRandom)
        etTeamNumber = findViewById(R.id.etTeamNumber)
        rvPokemon = findViewById(R.id.rvPokemon)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        rvPokemon.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        val btRandom = findViewById<Button>(R.id.btRandom)
        val etTeamNumber = findViewById<EditText>(R.id.etTeamNumber)
        val ibFavorite = findViewById<ImageButton>(R.id.ibFavorite)

        btRandom.setOnClickListener {
            val number = etTeamNumber.text.toString().toInt()
            if (number < 7) {
                loadPokemon(number, teamId = 1) { pokemonList ->
                    this.pokemonList = pokemonList
                    val rvPokemon = findViewById<RecyclerView>(R.id.rvPokemon)
                    rvPokemon.layoutManager = LinearLayoutManager(this)
                    rvPokemon.adapter = PokemonAdapter(pokemonList, this)
                }
            } else {
                Toast.makeText(this, "Team size must be less than 7", Toast.LENGTH_SHORT).show()
            }
        }

        ibFavorite.setOnClickListener {
            val dao = AppDatabase.getInstance(this).getDao()
            val teamId = dao.getAllTeamIds().size + 1
            val teamPokemon = pokemonList.map { it.copy(teamId = teamId) }
            dao.insertAll(teamPokemon)
            Toast.makeText(this, "Team added to favorites", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadPokemon(number: Int, teamId: Int, onComplete: (List<Pokemon>) -> Unit) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val pokeApiService: PokeApiService = retrofit.create(PokeApiService::class.java)
        val pokemonList = mutableListOf<Pokemon>()

        fun fetchPokemon(remaining: Int) {
            if (remaining <= 0) {
                onComplete(pokemonList)
                return
            }

            val randomId = Random.nextInt(1, 898)
            val request = pokeApiService.getPokemon(randomId)
            request.enqueue(object : Callback<PokemonResponse> {
                override fun onResponse(call: Call<PokemonResponse>, response: Response<PokemonResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            pokemonList.add(it.toPokemon(teamId))
                            fetchPokemon(remaining - 1)
                        }
                    } else {
                        fetchPokemon(remaining)
                    }
                }

                override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {
                    fetchPokemon(remaining)
                }
            })
        }

        fetchPokemon(number)
    }

    override fun onItemClick(pokemon: Pokemon) {
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}