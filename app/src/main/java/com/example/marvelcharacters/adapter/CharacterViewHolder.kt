package com.example.marvelcharacters.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelcharacters.R

class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val characterImage: ImageView = itemView.findViewById(R.id.imageViewCharacterImage)
    val characterName: TextView = itemView.findViewById(R.id.textViewCharacterName)
    val characterSeriesCount: TextView = itemView.findViewById(R.id.textViewSeriesCount)
    val cardView: CardView = itemView.findViewById(R.id.cardView)
    val imageViewFavorite: ImageView = itemView.findViewById(R.id.imageViewFavorite)
}