package com.hcraestrak.kartsearch.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hcraestrak.domain.model.local.Bookmark
import com.hcraestrak.kartsearch.databinding.ItemBookmarkBinding
import com.hcraestrak.kartsearch.view.adapter.listener.OnItemClickListener

class BookmarkRecyclerViewAdapter: RecyclerView.Adapter<BookmarkRecyclerViewAdapter.ViewHolder>() {

    private val dataSet = mutableListOf<Bookmark>()
    private var word: String = ""
    private lateinit var mListener: OnItemClickListener

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        mListener = object : OnItemClickListener {
            override fun onClick(id: Int) {
                listener(id)
            }
        }
    }

    fun getWord() = word

    fun setData(data: List<Bookmark>) {
        dataSet.clear()
        dataSet.addAll(data)
        notifyDataSetChanged()
    }

    fun clearData() {
        dataSet.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemBookmarkBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Bookmark) {
            binding.bookmark = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBookmarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])

        holder.binding.itemDeleteBookmark.setOnClickListener {
            word = dataSet[position].nickName
            dataSet.remove(Bookmark(word))
            notifyDataSetChanged()
            mListener.onClick(1)
        }

        holder.binding.itemBookmark.setOnClickListener {
            word = dataSet[position].nickName
            mListener.onClick(2)
        }
    }

    override fun getItemCount() = dataSet.size
}