package com.example.marvelcharacters.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelcharacters.R
import com.example.marvelcharacters.model.Character
import com.example.marvelcharacters.model.CharacterAdapter
import com.example.marvelcharacters.viewmodels.DetailFragmentViewModel
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val itemId = arguments?.getString("itemId", "0")?: 0
        val id = itemId.toString()
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewDetail)
        val viewModel = DetailFragmentViewModel(id = id)

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)

        val adapter = CharacterAdapter(Character(), requireContext())
        recyclerView.adapter = adapter

        viewModel.character.observe(viewLifecycleOwner, Observer { character ->
            adapter.updateCharacter(character)
        })

        lifecycleScope.launch {
            viewModel.getCharacter()
            Log.d("characterlist", "onCreateView: ${viewModel.character}")
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        return view
    }
}