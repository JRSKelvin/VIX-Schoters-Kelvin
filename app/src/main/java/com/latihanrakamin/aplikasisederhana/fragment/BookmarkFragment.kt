package com.latihanrakamin.aplikasisederhana.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.latihanrakamin.aplikasisederhana.adapter.BookmarkRecyclerView
import com.latihanrakamin.aplikasisederhana.database.data.TopHeadlinesData
import com.latihanrakamin.aplikasisederhana.databinding.FragmentBookmarkBinding
import com.latihanrakamin.aplikasisederhana.viewmodel.DatabaseViewModel

class BookmarkFragment : Fragment() {
    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!
    private lateinit var databaseViewModel: DatabaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseViewModel = ViewModelProvider(this)[DatabaseViewModel::class.java]
        binding.recyclerViewListContent.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.swipeRefreshLayout.setOnRefreshListener {
            refreshData()
        }
        refreshData()
    }

    private fun refreshData() {
        binding.progressBarLoading.visibility = View.VISIBLE
        databaseViewModel.getTopHeadlines().observe(viewLifecycleOwner) { itRes ->
            if (itRes.isNotEmpty()) {
                println("Data Database $itRes")
                showRecyclerView(itRes)
            }
            binding.progressBarLoading.visibility = View.GONE
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun showRecyclerView(data: List<TopHeadlinesData>?) {
        if (data != null) {
            val adapter = BookmarkRecyclerView(data, object : BookmarkRecyclerView.OnClickListener {
                override fun onClickItem(dataSingle: TopHeadlinesData) {
                    val navigateDestination = BookmarkFragmentDirections.actionBookmarkFragmentToDetailFragment(dataSingle)
                    requireView().findNavController().navigate(navigateDestination)
                }

                override fun onDeleteItem(dataSingle: TopHeadlinesData) {
                    AlertDialog.Builder(requireContext())
                        .setTitle("Are You Sure")
                        .setMessage("Are You Sure Want Delete This '${dataSingle.title}' Bookmark")
                        .setPositiveButton("Yes") { _, _ ->
                            databaseViewModel.deleteTopHeadlines(dataSingle).observe(viewLifecycleOwner) {
                                println("Return Bookmark Delete Result $it")
                                if (it == 1) {
                                    Toast.makeText(requireContext(), "Bookmark Delete Successfully", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(requireContext(), "Bookmark Delete Failed", Toast.LENGTH_SHORT).show()
                                }
                                refreshData()
                            }
                        }
                        .setNegativeButton("No") { dialog, _ -> dialog.cancel() }
                        .show()
                }
            })
            binding.recyclerViewListContent.adapter = adapter
        }
    }
}