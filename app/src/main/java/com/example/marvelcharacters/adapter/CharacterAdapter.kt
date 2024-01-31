package com.example.marvelcharacters.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelcharacters.R
import com.example.marvelcharacters.db.CharactersDatabase
import com.example.marvelcharacters.fragments.FavoritesFragmentDirections
import com.example.marvelcharacters.fragments.HomeFragmentDirections
import com.example.marvelcharacters.model.Character
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterAdapter(private var characterList: List<Character>) : RecyclerView.Adapter<CharacterViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_item, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val currentCharacter = characterList[position]

        val imageUrl = currentCharacter.thumbnail + "." + currentCharacter.thumbnailExtension
        val newImageUrl = imageUrl.replace("http://", "https://")
        Picasso.get().load(newImageUrl).into(holder.characterImage)
        holder.characterName.text = currentCharacter.name
        holder.characterSeriesCount.text= "Series Count: ${currentCharacter.series.size}"
        holder.cardView.setOnClickListener{
            val currentFragment = holder.itemView.findNavController().currentDestination
            if (currentFragment?.id == R.id.homeFragment){
                val actionHome = HomeFragmentDirections.actionHomeFragmentToDetailFragment()
                actionHome.arguments.putInt("itemId", currentCharacter.id)
                holder.itemView.findNavController().navigate(actionHome)
            }else if (currentFragment?.id == R.id.favoritesFragment){
                val actionFavorites = FavoritesFragmentDirections.actionFavoritesFragmentToDetailFragment()
                actionFavorites.arguments.putInt("itemId", currentCharacter.id)
                holder.itemView.findNavController().navigate(actionFavorites)
            }
        }
        CoroutineScope(Dispatchers.Main).launch {
            val charactersDatabase = CharactersDatabase.getInstance(holder.itemView.context.applicationContext)
            val charactersDao = charactersDatabase?.getCharacterDao()

            val characterId = currentCharacter.id
            val isCharacterInDatabase = charactersDao!!.isCharacterInDatabase(characterId) > 0

            holder.imageViewFavorite.visibility = if (isCharacterInDatabase) View.VISIBLE else View.GONE
        }
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    fun update(updateList: List<Character>){
        characterList = updateList
        notifyDataSetChanged()
    }
}