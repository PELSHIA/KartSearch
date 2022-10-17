package com.hcraestrak.kartsearch.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hcraestrak.domain.model.local.Search
import com.hcraestrak.kartsearch.databinding.ItemSearchBinding
import com.hcraestrak.kartsearch.view.adapter.listener.OnItemClickListener

class SearchRecyclerViewAdapter: RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder>() {

    private val dataSet = mutableListOf<Search>()
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

    fun setData(data: List<Search>) {
        dataSet.clear()
        dataSet.addAll(data)
        notifyDataSetChanged()
    }

    fun clearData() {
        dataSet.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemSearchBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Search) {
            binding.search = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])

        holder.binding.itemDelete.setOnClickListener {
            word = dataSet[position].word
            dataSet.remove(Search(word))
            notifyDataSetChanged()
            mListener.onClick(1)
        }

        holder.binding.itemSearches.setOnClickListener {
            word = dataSet[position].word
            mListener.onClick(2)
        }
    }

    override fun getItemCount() = dataSet.size
}