package com.example.marvelcharacters.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelcharacters.R
import com.example.marvelcharacters.item.ItemAdapter
import com.example.marvelcharacters.viewmodels.HomeFragmentViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel = HomeFragmentViewModel(offset = "0")
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        val viewButton = view.findViewById<ImageButton>(R.id.viewButton)

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

        val adapter = ItemAdapter(emptyList())
        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object:RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount
                if (lastVisibleItemPosition == totalItemCount-1){
                    viewModel.updateOffset()
                    lifecycleScope.launch {
                        viewModel.getData()
                    }
                }
            }
        })

        viewModel.getItemList().observe(viewLifecycleOwner, Observer { items ->
            adapter.updateItems(items)
        })

        lifecycleScope.launch {
            viewModel.getData()
        }

        return view
    }
}