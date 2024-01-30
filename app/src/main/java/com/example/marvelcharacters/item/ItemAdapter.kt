package com.example.marvelcharacters.item

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelcharacters.R
import com.example.marvelcharacters.fragments.HomeFragmentDirections
import com.squareup.picasso.Picasso

class ItemAdapter(private var itemList: List<Item>) : RecyclerView.Adapter<ItemViewHolder>()  {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_item, parent, false)
            return ItemViewHolder(view)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val currentItem = itemList[position]

            Picasso.get().load(currentItem.imageUrl).into(holder.imageView)
            holder.textView.text = currentItem.text
            holder.textView1.text = "Series Count: ${currentItem.text1}"
            holder.cardView.setOnClickListener{
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment()
                action.arguments.putString("itemId", itemList[position].id)
                holder.itemView.findNavController().navigate(action)
            }
        }

        override fun getItemCount(): Int {
            return itemList.size
        }

        fun update(updateList: List<Item>){
            itemList = updateList
            notifyDataSetChanged()
        }

        fun updateItems(newItems: List<Item>) {
            val oldSize = itemList.size
            itemList = itemList + newItems
            notifyItemRangeInserted(oldSize, newItems.size)
            Log.d("kfk", "updateItems: ${itemList.size}")
        }
    }