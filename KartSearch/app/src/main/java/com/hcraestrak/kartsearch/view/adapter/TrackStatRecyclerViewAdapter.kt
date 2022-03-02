package com.hcraestrak.kartsearch.view.adapter

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.hcraestrak.kartsearch.R
import com.hcraestrak.kartsearch.databinding.ItemTrackBinding
import com.hcraestrak.kartsearch.databinding.ItemTrackHeaderBinding
import com.hcraestrak.kartsearch.view.adapter.data.TrackStatData
import java.io.File
import java.io.IOException
import kotlin.math.roundToInt

class TrackStatRecyclerViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER: Int = 0
    private val TYPE_ITEM: Int = 1
    private val dataList: MutableList<TrackStatData> = mutableListOf()

    fun clearData() {
        dataList.clear()
        notifyDataSetChanged()
    }

    fun setData(data: List<TrackStatData>, isFirst: Boolean) {
        if (isFirst) {
            dataList.add(TrackStatData(" ", " ", " ", " ", " "))
        }
        dataList.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemTrackBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: TrackStatData) {
            binding.trackData = data
        }
    }

    inner class HeaderViewHolder(val binding: ItemTrackHeaderBinding): RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_ITEM) {
            val binding = ItemTrackBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ViewHolder(binding)
        } else {
            val binding = ItemTrackHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            HeaderViewHolder(binding)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(dataList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (dataList[position].track == " ") {
            TYPE_HEADER
        } else {
            TYPE_ITEM
        }
    }

    override fun getItemCount() = dataList.size
}