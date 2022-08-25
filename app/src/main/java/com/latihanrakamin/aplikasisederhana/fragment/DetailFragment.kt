package com.latihanrakamin.aplikasisederhana.fragment

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.latihanrakamin.aplikasisederhana.database.data.TopHeadlinesData
import com.latihanrakamin.aplikasisederhana.databinding.FragmentDetailBinding
import com.latihanrakamin.aplikasisederhana.viewmodel.DatabaseViewModel

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var databaseViewModel: DatabaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseViewModel = ViewModelProvider(this)[DatabaseViewModel::class.java]
        val argumentData = DetailFragmentArgs.fromBundle(arguments as Bundle).newsDataParcelize
        println(argumentData)

        binding.apply {
            Glide.with(binding.root)
                .load(argumentData.urlToImage)
                .into(imageViewNewsDetail)
            newsTitleValue.text = argumentData.title
            newsSourceValue.text = argumentData.source
            newsPublishedAtValue.text = argumentData.publishedAt
            newsDescriptionValue.text = argumentData.description
            newsAuthorUploadValue.text = argumentData.author
        }

        binding.buttonBackDetailTop.setOnClickListener { itView ->
            itView.findNavController().navigateUp()
        }

        binding.buttonBookmarkDetailTop.setOnClickListener {
            if (argumentData.idPrimary == -1) {
                val bookmarkValue = TopHeadlinesData(
                    idPrimary = 0,
                    author = argumentData.author,
                    content = argumentData.content,
                    description = argumentData.description,
                    publishedAt = argumentData.publishedAt,
                    source = argumentData.source,
                    title = argumentData.title,
                    url = argumentData.url,
                    urlToImage = argumentData.urlToImage
                )
                databaseViewModel.insertTopHeadlines(bookmarkValue).observe(viewLifecycleOwner) {
                    println("Return Bookmark Save Result $it")
                    if (it >= 1L) {
                        Toast.makeText(requireContext(), "Bookmark Successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Bookmark Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "You Already Bookmarked This", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonShareDetailTop.setOnClickListener {
            val shareLink = argumentData.url
            AlertDialog.Builder(requireContext())
                .setTitle("Choose To Share Or Open Link")
                .setMessage("Please Choose What Do You Want To Do With The URL Link")
                .setPositiveButton("Open In Browser") { _, _ ->
                    val intentShare = Intent(Intent.ACTION_VIEW, Uri.parse(shareLink))
                    startActivity(intentShare)
                }
                .setNegativeButton("Share Link") { _, _ ->
                    val intentShare = Intent()
                    intentShare.action = Intent.ACTION_SEND
                    intentShare.putExtra(Intent.EXTRA_TEXT, "Click The News URL To View More Detail $shareLink")
                    intentShare.type = "text/plain"
                    startActivity(Intent.createChooser(intentShare, "Share To:"))
                }
                .setNeutralButton("Cancel") { dialog, _ -> dialog.cancel() }
                .show()
        }
    }
}