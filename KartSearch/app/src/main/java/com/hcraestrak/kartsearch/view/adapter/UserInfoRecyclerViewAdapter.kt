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
import com.hcraestrak.kartsearch.R
import com.hcraestrak.kartsearch.view.adapter.data.UserInfoData

class UserInfoRecyclerViewAdapter: RecyclerView.Adapter<UserInfoRecyclerViewAdapter.ViewHolder>() {

    private val data = mutableListOf<UserInfoData>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rank: TextView = view.findViewById(R.id.item_rank)
        val kart: ImageView = view.findViewById(R.id.item_kart_img)
        val map: TextView = view.findViewById(R.id.item_map)
        val time: TextView = view.findViewById(R.id.item_time)

        fun bind(data: UserInfoData, context: Context) {
            rank.text = "${data.userRank.toString()}/${data.playerCount.toString()}"
            Glide.with(context).load(data.kart).into(kart)
            map.text = data.track
            time.text = data.time.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_record, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size
}