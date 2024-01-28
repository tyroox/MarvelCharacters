package com.example.marvelcharacters.model

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelcharacters.R

class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val characterImage: ImageView = itemView.findViewById(R.id.imageViewCharacterImageDetail)
    val nameText: TextView = itemView.findViewById(R.id.textViewCharacterNameDetail)
    val seriesText: TextView = itemView.findViewById(R.id.textViewSeriesDetail)
    val storyNamesText: TextView = itemView.findViewById(R.id.textViewStoryNamesDetail)
    val eventsText: TextView = itemView.findViewById(R.id.textViewCharacterEventsDetail)
    val comicsText: TextView = itemView.findViewById(R.id.textViewCharacterComicsDetail)
}