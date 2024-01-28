package com.example.marvelcharacters.model

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelcharacters.R
import com.squareup.picasso.Picasso

class CharacterAdapter (private var character: Character) : RecyclerView.Adapter<CharacterViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_character, parent, false)
        return CharacterViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val currentCharacter = character
        val imageUrl = currentCharacter.thumbnail + "." + currentCharacter.thumbnailExtension
        val newImageUrl = imageUrl.replace("http://", "https://")
        Log.d("imageurl", "onBindViewHolder: $newImageUrl")
        Picasso.get().load(newImageUrl).into(holder.characterImage)
        holder.nameText.text = currentCharacter.name

        if (currentCharacter.series.isEmpty()){
            holder.seriesText.text = "No series found for ${currentCharacter.name}."
        }
        else{
            holder.seriesText.text = ""
            for (i in currentCharacter.series.indices) {
                holder.seriesText.append("${i + 1}) " + currentCharacter.series[i] + "\n")

            }
        }

        if (currentCharacter.stories.isEmpty()){
            holder.storyNamesText.text = "No stories found for ${currentCharacter.name}."
        }
        else{
            holder.storyNamesText.text = ""
            for (i in currentCharacter.stories.indices) {
                holder.storyNamesText.append("${i + 1}) " + currentCharacter.stories[i] + "\n")
            }
        }

        if (currentCharacter.events.isEmpty()){
            holder.eventsText.text = "No events found for ${currentCharacter.name}."
        }
        else{
            holder.eventsText.text = ""
            for (i in currentCharacter.events.indices) {
                holder.eventsText.append("${i + 1}) " + currentCharacter.events[i] + "\n")
            }
        }

        if (currentCharacter.comics.isEmpty()){
            holder.comicsText.text = "No comics found for ${currentCharacter.name}."
        }
        else{
            holder.comicsText.text = ""
            for (i in currentCharacter.comics.indices) {
                holder.comicsText.append("${i + 1}) " + currentCharacter.comics[i] + "\n")
            }
        }
    }

    override fun getItemCount(): Int {
        return 1
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateCharacter(newCharacter: Character){
        character = newCharacter
        notifyDataSetChanged()
    }
}