package com.example.app_pokemonteamassemble.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app_pokemonteamassemble.R
import com.example.app_pokemonteamassemble.models.Pokemon
import com.squareup.picasso.Picasso

class TeamAdapter(
    private val teams: List<List<Pokemon>>
) : RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.prototype_team, parent, false)
        return TeamViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val team = teams[position]
        holder.bind(team)
    }

    override fun getItemCount() = teams.size

    class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTeamId: TextView = itemView.findViewById(R.id.tvTeamId)
        private val pokemonContainer: LinearLayout = itemView.findViewById(R.id.pokemonContainer)

        fun bind(team: List<Pokemon>) {
            tvTeamId.text = "Team ${team.first().teamId}"
            pokemonContainer.removeAllViews()
            team.forEach { pokemon ->
                val imageView = ImageView(itemView.context).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                }
                Picasso.get()
                    .load(pokemon.url)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(imageView)
                pokemonContainer.addView(imageView)
            }
        }
    }
}