package com.hcraestrak.kartsearch.view.adapter

import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.hcraestrak.kartsearch.R
import com.hcraestrak.kartsearch.view.adapter.data.RankData
import com.hcraestrak.kartsearch.view.adapter.listener.OnItemClickListener
import java.io.File
import java.io.IOException

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
        dataSet.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val rank: TextView = view.findViewById(R.id.item_rank)
        private val kart: ImageView = view.findViewById(R.id.item_rank_kart_img)
        val nickName: TextView = view.findViewById(R.id.item_rank_nickName)
        private val time: TextView = view.findViewById(R.id.item_rank_time)

        fun bind(data: RankData) {
            nickName.text = data.nickName
            if (data.isRetire == "0") {
                rank.text = data.rank
                time.text = getTime(data.time.toInt())
            } else {
                rank.text = "Re"
                time.text = "-"
            }

            getImage(data.kart)
            setTextColor(data.teamId, data.isWin)
        }

        private fun setTextColor(teamId: String, isWin: String){
            if (teamId == "2") {
                rank.setTextColor(Color.parseColor("#7CA8FF"))
            } else if (teamId == "1") {
                rank.setTextColor(Color.parseColor("#FF8484"))
            }

            if (isWin == "1") {
                nickName.setTextColor(Color.parseColor("#7CA8FF"))
            }
        }

        private fun getTime(time: Int): String {
            var min: Int = 0
            var sec: Int = time / 1000
            val mSec: Int = time % 1000
            while (sec > 60) {
                sec -= 60
                min++
            }

            return String.format("%02d:%02d.%03d", min, sec, mSec)
        }

        private fun getImage(kartId: String) {
            val storageReference = FirebaseStorage.getInstance().getReference("/kart/$kartId.png")
            try {
                val localFile: File = File.createTempFile("tempfile", ".png")
                storageReference.getFile(localFile)
                    .addOnSuccessListener {
                        val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                        Glide.with(itemView.context).load(bitmap).into(kart)
                    }.addOnFailureListener{
                        Toast.makeText(itemView.context, "사진 가져오기에 실패했습니다.", Toast.LENGTH_LONG).show()
                    }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rank, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])

        holder.nickName.setOnClickListener {
            nickName = dataSet[position].nickName
            mListener.onClick(1)
        }
    }

    override fun getItemCount() = dataSet.size
}