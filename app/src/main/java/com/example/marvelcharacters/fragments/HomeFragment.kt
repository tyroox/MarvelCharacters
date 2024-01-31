package com.example.marvelcharacters.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelcharacters.R
import com.example.marvelcharacters.adapter.CharacterAdapter
import com.example.marvelcharacters.viewmodels.HomeFragmentViewModel
import com.example.marvelcharacters.viewmodels.SearchViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel = HomeFragmentViewModel(offset = 0)
        val searchViewModel = SearchViewModel(offset = 0)
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        val viewButton = view.findViewById<ImageButton>(R.id.viewButton)
        val sortButton = view.findViewById<ImageButton>(R.id.sortButton)
        val editText = view.findViewById<EditText>(R.id.editTextSearch)
        val searchButton = view.findViewById<ImageButton>(R.id.searchButton)
        var query = ""
        var isSearch = false

        val adapter = CharacterAdapter(emptyList())
        recyclerView.adapter = adapter

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        viewButton.setOnClickListener{
            val currentLayoutManager = recyclerView.layoutManager
            recyclerView.layoutManager = null

            if (viewButton.tag == null || viewButton.tag == "default") {
                viewButton.setImageResource(R.drawable.ic_grid)
                viewButton.tag = "clicked"
                recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)
            } else {
                viewButton.setImageResource(R.drawable.ic_list)
                viewButton.tag = "default"
                recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
            }

            recyclerView.layoutManager?.onRestoreInstanceState(currentLayoutManager?.onSaveInstanceState())
        }

        sortButton.setOnClickListener {
            viewModel.sortCharacterNames()
            isSearch = false
            val toast = Toast.makeText(context, "Characters are sorted!", Toast.LENGTH_SHORT)
            toast.show()
            lifecycleScope.launch {
                viewModel.deleteItems()
                viewModel.getData()
            }
        }

        editText.doAfterTextChanged { editable ->
            searchButton.setOnClickListener{
                isSearch = true
                query = editable.toString().trim()
                if (query.isEmpty()){
                    val toast = Toast.makeText(context, "Empty search!", Toast.LENGTH_SHORT)
                    toast.show()
                }
                else{
                    lifecycleScope.launch {
                        editText.setText("")
                        searchViewModel.resetOffset()
                        viewModel.deleteItems()
                        searchViewModel.deleteItems()
                        searchViewModel.search(query)
                    }
                }
                searchViewModel.liveCharacterList.observe(viewLifecycleOwner, Observer {
                    adapter.update(it)
                })
            }
        }

        recyclerView.addOnScrollListener(object:RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount
                if (lastVisibleItemPosition == totalItemCount-1){
                    lifecycleScope.launch {
                        if (!isSearch){
                            viewModel.updateOffset()
                            viewModel.getData()
                        }else{
                            searchViewModel.updateOffset()
                            searchViewModel.search(query)
                        }
                    }
                }
            }
        })

        viewModel.liveCharacterList.observe(viewLifecycleOwner, Observer {
            adapter.update(it)
        })

        lifecycleScope.launch {
            viewModel.getData()
        }

        return view
    }
}