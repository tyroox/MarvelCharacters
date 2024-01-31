package com.example.marvelcharacters.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.marvelcharacters.R
import com.example.marvelcharacters.db.CharactersDatabase
import com.example.marvelcharacters.viewmodels.DetailFragmentViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val itemId = arguments?.getInt("itemId", 0) ?: 0
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        val viewModel = DetailFragmentViewModel(id = itemId)
        val characterImage = view.findViewById<ImageView>(R.id.imageViewCharacterImageDetail)
        val characterName = view.findViewById<TextView>(R.id.textViewCharacterNameDetail)
        val seriesText = view.findViewById<TextView>(R.id.textViewSeriesDetail)
        val storyNamesText = view.findViewById<TextView>(R.id.textViewStoryNamesDetail)
        val eventsText = view.findViewById<TextView>(R.id.textViewCharacterEventsDetail)
        val comicsText = view.findViewById<TextView>(R.id.textViewCharacterComicsDetail)
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.btm_nav)
        val favoriteButton = view.findViewById<ImageButton>(R.id.buttonFavorite)
        val backButton = view.findViewById<ImageButton>(R.id.buttonBack)
        val charactersDatabase = CharactersDatabase.getInstance(requireContext())



        viewModel.liveCharacter.observe(viewLifecycleOwner, Observer { character ->
            val imageUrl = character.thumbnail + "." + character.thumbnailExtension
            val newImageUrl = imageUrl.replace("http://", "https://")
            Picasso.get().load(newImageUrl).into(characterImage)
            characterName.text = character.name
            if (character.series.isEmpty()) {
                seriesText.text = "No series found for ${character.name}."
            } else {
                seriesText.text = ""
                for (i in character.series.indices) {
                    seriesText.append("${i + 1}) " + character.series[i] + "\n")
                }
            }

            if (character.stories.isEmpty()) {
                storyNamesText.text = "No stories found for ${character.name}."
            } else {
                storyNamesText.text = ""
                for (i in character.stories.indices) {
                    storyNamesText.append("${i + 1}) " + character.stories[i] + "\n")
                }
            }

            if (character.events.isEmpty()) {
                eventsText.text = "No events found for ${character.name}."
            } else {
                eventsText.text = ""
                for (i in character.events.indices) {
                    eventsText.append("${i + 1}) " + character.events[i] + "\n")
                }
            }

            if (character.comics.isEmpty()) {
                comicsText.text = "No comics found for ${character.name}."
            } else {
                comicsText.text = ""
                for (i in character.comics.indices) {
                    comicsText.append("${i + 1}) " + character.comics[i] + "\n")
                }
            }
        })

        lifecycleScope.launch {
            viewModel.getCharacter()
        }

        viewModel.liveCharacter.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                charactersDatabase?.let {
                    val charactersDao = it.getCharacterDao()
                    val characterId = viewModel.character.id
                    val isCharacterInDatabase = charactersDao.isCharacterInDatabase(characterId)
                    if (isCharacterInDatabase > 0) {
                        favoriteButton.setImageResource(R.drawable.ic_favorite_red)
                    } else {
                        favoriteButton.setImageResource(R.drawable.ic_favorite_white)
                    }
                }
            }
        }

        favoriteButton.setOnClickListener {
            lifecycleScope.launch {
                charactersDatabase?.let {
                    val charactersDao = it.getCharacterDao()
                    val characterId = viewModel.character.id
                    val isCharacterInDatabase = charactersDao.isCharacterInDatabase(characterId)
                    if (isCharacterInDatabase > 0) {
                        charactersDao.deleteCharacter(viewModel.character)
                        val toast = Toast.makeText(context, "Character is removed from favorites!", Toast.LENGTH_SHORT)
                        toast.show()
                        withContext(Dispatchers.Main) {
                            favoriteButton.setImageResource(R.drawable.ic_favorite_white)
                        }
                    } else {
                        charactersDao.upsert(viewModel.character)
                        val toast = Toast.makeText(context, "Character is added to favorites!", Toast.LENGTH_SHORT)
                        toast.show()
                        withContext(Dispatchers.Main) {
                            favoriteButton.setImageResource(R.drawable.ic_favorite_red)
                        }
                    }
                }
            }
        }


        if (bottomNavigationView != null) {
            bottomNavigationView.visibility = View.GONE;
        }

        backButton.setOnClickListener{
            findNavController().navigateUp()
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()

        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.btm_nav)

        if (bottomNavigationView != null) {
            bottomNavigationView.visibility = View.VISIBLE;
        }
    }
}
