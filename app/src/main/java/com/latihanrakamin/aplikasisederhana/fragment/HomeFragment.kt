package com.latihanrakamin.aplikasisederhana.fragment

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.latihanrakamin.aplikasisederhana.adapter.HomeRecyclerView
import com.latihanrakamin.aplikasisederhana.database.data.TopHeadlinesData
import com.latihanrakamin.aplikasisederhana.databinding.FragmentHomeBinding
import com.latihanrakamin.aplikasisederhana.remote.model.GetTopHeadlinesArticle
import com.latihanrakamin.aplikasisederhana.remote.network.ApiConfig
import com.latihanrakamin.aplikasisederhana.repository.ApiRepository
import com.latihanrakamin.aplikasisederhana.viewmodel.ApiViewModel
import com.latihanrakamin.aplikasisederhana.viewmodel.ApiViewModelFactory
import kotlin.system.exitProcess

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var apiViewModel: ApiViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        apiViewModel = ViewModelProvider(this, ApiViewModelFactory(ApiRepository(ApiConfig.provideApiService)))[ApiViewModel::class.java]
        binding.recyclerViewListContent.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.buttonProfileHomeTop.setOnClickListener {
            view.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment())
        }
        binding.buttonBookmarkHomeTop.setOnClickListener {
            view.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToBookmarkFragment())
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            refreshData()
        }
        refreshData()
    }

    override fun onResume() {
        super.onResume()

        var timeBack: Long = 0
        requireView().isFocusableInTouchMode = true
        requireView().requestFocus()
        requireView().setOnKeyListener { _, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_UP && i == KeyEvent.KEYCODE_BACK) {
                if (System.currentTimeMillis() - timeBack > 1000) {
                    timeBack = System.currentTimeMillis()
                    Toast.makeText(requireContext(), "Press Again To Exit App", Toast.LENGTH_LONG).show()
                } else {
                    exitProcess(0)
                }
                true
            } else false
        }
    }

    private fun refreshData() {
        binding.progressBarLoading.visibility = View.VISIBLE
        apiViewModel.getTopHeadlines()
        apiViewModel.getTopHeadlines.observe(viewLifecycleOwner) { itRes ->
            val body = itRes.body()
            val code = itRes.code()
            if (code == 200) {
                println("Data Retrofit $body")
                showRecyclerView(body?.getTopHeadlinesArticles)
            }
            binding.progressBarLoading.visibility = View.GONE
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun showRecyclerView(data: List<GetTopHeadlinesArticle>?) {
        if (data != null) {
            val adapter = HomeRecyclerView(data, object : HomeRecyclerView.OnClickListener {
                override fun onClickItem(dataSingle: GetTopHeadlinesArticle) {
                    val passDataValue = TopHeadlinesData(
                        idPrimary = -1,
                        author = dataSingle.author,
                        content = dataSingle.content,
                        description = dataSingle.description,
                        publishedAt = dataSingle.publishedAt,
                        source = dataSingle.getTopHeadlinesSource.name,
                        title = dataSingle.title,
                        url = dataSingle.url,
                        urlToImage = dataSingle.urlToImage
                    )
                    val navigateDestination = HomeFragmentDirections.actionHomeFragmentToDetailFragment(passDataValue)
                    requireView().findNavController().navigate(navigateDestination)
                }
            })
            binding.recyclerViewListContent.adapter = adapter
        }
    }
}