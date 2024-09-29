package com.example.app_pokemonteamassemble.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_pokemonteamassemble.R
import com.example.app_pokemonteamassemble.adapters.TeamAdapter
import com.example.app_pokemonteamassemble.db.AppDatabase

class FavouriteTeamsActivity : AppCompatActivity() {
    private lateinit var rvTeams: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_teams)
        setSupportActionBar(findViewById(R.id.toolbar))

        rvTeams = findViewById(R.id.rvTeams)
        rvTeams.layoutManager = LinearLayoutManager(this)

        loadTeams()
    }

    private fun loadTeams() {
        val dao = AppDatabase.getInstance(this).getDao()
        val teamIds = dao.getAllTeamIds()
        val teams = teamIds.map { teamId -> dao.getTeam(teamId) }
        rvTeams.adapter = TeamAdapter(teams)
    }
}