package com.latihanrakamin.aplikasisederhana.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.latihanrakamin.aplikasisederhana.databinding.RecyclerViewListNewsBinding
import com.latihanrakamin.aplikasisederhana.remote.model.GetTopHeadlinesArticle

class HomeRecyclerView(
    private val newsList: List<GetTopHeadlinesArticle> = arrayListOf(),
    private val onItemClick: OnClickListener
) : RecyclerView.Adapter<HomeRecyclerView.ListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ListViewHolder(RecyclerViewListNewsBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = newsList[position]
        data.let { holder.bind(data) }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    inner class ListViewHolder(private val binding: RecyclerViewListNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dataSingle: GetTopHeadlinesArticle) {
            binding.apply {
                Glide.with(binding.root)
                    .load(dataSingle.urlToImage)
                    .into(circleImageViewList)
                listItemTitle.text = dataSingle.title
                listItemPublishAt.text = dataSingle.publishedAt
                root.setOnClickListener {
                    onItemClick.onClickItem(dataSingle)
                }
            }
        }
    }

    interface OnClickListener {
        fun onClickItem(dataSingle: GetTopHeadlinesArticle)
    }
}