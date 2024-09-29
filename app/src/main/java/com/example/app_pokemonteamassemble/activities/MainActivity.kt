package com.example.app_pokemonteamassemble.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.app_pokemonteamassemble.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupView()
    }
    private fun setupView(){
        var btAssemble = findViewById<Button>(R.id.btAssemble)
        var btTeam = findViewById<Button>(R.id.btTeams)

        btAssemble.setOnClickListener{
            val intent = Intent(this, AssembleActivity::class.java)
            startActivity(intent)
        }
        btTeam.setOnClickListener {
            val intent = Intent(this, FavouriteTeamsActivity::class.java)
            startActivity(intent)
        }
    }
}