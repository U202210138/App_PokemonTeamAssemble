package com.example.app_pokemonteamassemble.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app_pokemonteamassemble.R
import com.example.app_pokemonteamassemble.models.Pokemon
import com.squareup.picasso.Picasso

class PokemonAdapter(
    private val pokemonList: List<Pokemon>,
    private val clickListener: OnItemClickListener
) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(pokemon: Pokemon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.prototype_pokemon, parent, false)
        return PokemonViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val currentPokemon = pokemonList[position]
        holder.bind(currentPokemon, clickListener)
    }

    override fun getItemCount() = pokemonList.size

    class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivPokemon: ImageView = itemView.findViewById(R.id.ivPokemon)
        private val tvName: TextView = itemView.findViewById(R.id.tvName)

        fun bind(pokemon: Pokemon, clickListener: OnItemClickListener) {
            tvName.text = pokemon.name
            Picasso.get()
                .load(pokemon.url)
                .placeholder(R.drawable.loading)
                .error(R.drawable.ic_launcher_foreground)
                .into(ivPokemon, object : com.squareup.picasso.Callback {
                    override fun onSuccess() {
                        // Image loaded successfully
                    }

                    override fun onError(e: Exception?) {
                        Log.e("PokemonAdapter", "Error loading image", e)
                    }
                })
            itemView.setOnClickListener {
                clickListener.onItemClick(pokemon)
            }
        }
    }
}