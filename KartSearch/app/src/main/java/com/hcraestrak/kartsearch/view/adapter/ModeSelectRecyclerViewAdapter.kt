package com.hcraestrak.kartsearch.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hcraestrak.kartsearch.R

class ModeSelectRecyclerViewAdapter: RecyclerView.Adapter<ModeSelectRecyclerViewAdapter.ViewHolder>() {

    private val data = arrayListOf<String>()
    private lateinit var mListener: OnItemClickListener
    private val mode: String = ""

    interface OnItemClickListener {
        fun onClick(id: Int)
    }

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        mListener = object : OnItemClickListener {
            override fun onClick(id: Int) {
                listener(id)
            }
        }
    }

    fun getMode() = mode

    fun setData(data: Array<String>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val mode: TextView = view.findViewById(R.id.item_mode)

        fun bind(data: String) {
            mode.text = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mode, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])

        holder.itemView.setOnClickListener {
            mListener.onClick(1 )
        }
    }

    override fun getItemCount() = data.size
}