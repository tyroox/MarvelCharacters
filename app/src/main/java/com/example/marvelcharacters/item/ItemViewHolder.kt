package com.example.marvelcharacters.item

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelcharacters.R

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageView: ImageView = itemView.findViewById(R.id.imageViewCharacterImage)
    val textView: TextView = itemView.findViewById(R.id.textViewCharacterName)
    val textView1: TextView = itemView.findViewById(R.id.textViewSeriesCount)
    val cardView: CardView = itemView.findViewById(R.id.cardView)
}