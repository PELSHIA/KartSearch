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
import com.hcraestrak.kartsearch.view.adapter.data.TrackStatData
import java.io.File
import java.io.IOException
import kotlin.math.roundToInt

class TrackStatRecyclerViewAdapter: RecyclerView.Adapter<TrackStatRecyclerViewAdapter.ViewHolder>() {

    private val dataList: MutableList<TrackStatData> = mutableListOf()

    fun clearData() {
        dataList.clear()
        notifyDataSetChanged()
    }

    fun setData(data: List<TrackStatData>) {
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemTrackBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: TrackStatData) {
            binding.trackData = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTrackBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount() = dataList.size
}