package com.latihanrakamin.aplikasisederhana.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.latihanrakamin.aplikasisederhana.database.data.TopHeadlinesData
import com.latihanrakamin.aplikasisederhana.databinding.RecyclerViewListNewsBookmarkBinding

class BookmarkRecyclerView(
    private val newsList: List<TopHeadlinesData> = arrayListOf(),
    private val onItemClick: OnClickListener
) : RecyclerView.Adapter<BookmarkRecyclerView.ListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ListViewHolder(RecyclerViewListNewsBookmarkBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = newsList[position]
        data.let { holder.bind(data) }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    inner class ListViewHolder(private val binding: RecyclerViewListNewsBookmarkBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dataSingle: TopHeadlinesData) {
            binding.apply {
                Glide.with(binding.root)
                    .load(dataSingle.urlToImage)
                    .into(circleImageViewList)
                listItemTitle.text = dataSingle.title
                listItemPublishAt.text = dataSingle.publishedAt
                root.setOnClickListener {
                    onItemClick.onClickItem(dataSingle)
                }
                buttonDeleteHomeTop.setOnClickListener {
                    onItemClick.onDeleteItem(dataSingle)
                }
            }
        }
    }

    interface OnClickListener {
        fun onClickItem(dataSingle: TopHeadlinesData)
        fun onDeleteItem(dataSingle: TopHeadlinesData)
    }
}