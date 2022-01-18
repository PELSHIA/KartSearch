package com.hcraestrak.kartsearch.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hcraestrak.kartsearch.R
import com.hcraestrak.kartsearch.model.db.entity.Search
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

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val searches: TextView = view.findViewById(R.id.item_searches)
        val delete: ImageButton = view.findViewById(R.id.item_delete)

        fun bind(data: Search) {
            searches.text = data.word
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
        holder.delete.setOnClickListener {
            mListener.onClick(1)
            word = dataSet[position].word
        }

        holder.searches.setOnClickListener {
            mListener.onClick(2)
            word = dataSet[position].word
        }
    }

    override fun getItemCount() = dataSet.size
}