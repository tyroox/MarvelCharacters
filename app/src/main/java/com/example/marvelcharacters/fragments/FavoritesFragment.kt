package com.example.marvelcharacters.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelcharacters.R
import com.example.marvelcharacters.adapter.CharacterAdapter
import com.example.marvelcharacters.db.CharactersDatabase

class FavoritesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewFavorites)
        recyclerView.layoutManager = GridLayoutManager(requireContext(),2)

        val characterAdapter = CharacterAdapter(emptyList())
        recyclerView.adapter = characterAdapter

        val charactersDao = CharactersDatabase.getInstance(requireContext())?.getCharacterDao()
        charactersDao?.getAllCharacters()?.observe(viewLifecycleOwner, Observer { characters ->
            characterAdapter.update(characters)
        })
    }
}