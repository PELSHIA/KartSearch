package com.hcraestrak.kartsearch.view.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hcraestrak.kartsearch.databinding.ItemRankBinding
import com.hcraestrak.kartsearch.view.adapter.data.RankData
import com.hcraestrak.kartsearch.view.adapter.listener.OnItemClickListener

class RankRecyclerViewAdapter: RecyclerView.Adapter<RankRecyclerViewAdapter.ViewHolder>() {

    private val dataSet = mutableListOf<RankData>()
    private var nickName: String = ""
    private lateinit var mListener: OnItemClickListener

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        mListener = object : OnItemClickListener {
            override fun onClick(id: Int) {
                listener(id)
            }
        }
    }

    fun getNickName() = nickName

    fun setData(data: List<RankData>) {
        dataSet.clear()
        dataSet.addAll(data.distinct())
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemRankBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(data: RankData) {
            binding.rank = data
            setTextColor(data.teamId, data.isWin)
        }

        private fun setTextColor(teamId: String, isWin: String){
            if (teamId == "2") {
                binding.itemRank.setTextColor(Color.parseColor("#7CA8FF"))
            } else if (teamId == "1") {
                binding.itemRank.setTextColor(Color.parseColor("#FF8484"))
            }

            if (isWin == "1") {
                binding.itemRankNickName.setTextColor(Color.parseColor("#7CA8FF"))
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRankBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])

        holder.binding.itemRankNickName.setOnClickListener {
            nickName = dataSet[position].nickName
            mListener.onClick(1)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount() = dataSet.size
}