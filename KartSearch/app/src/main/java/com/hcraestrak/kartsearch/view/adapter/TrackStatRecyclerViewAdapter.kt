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
import com.hcraestrak.kartsearch.view.adapter.data.TrackStatData
import java.io.File
import java.io.IOException
import kotlin.math.roundToInt

class TrackStatRecyclerViewAdapter: RecyclerView.Adapter<TrackStatRecyclerViewAdapter.ViewHolder>() {

    private val dataList: MutableList<TrackStatData> = mutableListOf()

    fun setData(data: List<TrackStatData>) {
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val trackImg: ImageView = view.findViewById(R.id.item_track_img)
        private val number: TextView = view.findViewById(R.id.item_track_number)
        private val win: TextView = view.findViewById(R.id.item_track_win)
        private val avg: TextView = view.findViewById(R.id.item_track_avg)
        val time: TextView = view.findViewById(R.id.item_track_time)

        fun bind(data: TrackStatData) {
            getImage(data.track)
            number.text = data.number.toString() + "회"
            win.text = (data.win.toDouble() / data.number.toDouble() * 100.0).roundToInt().toString() + "%"
            avg.text = (data.avg / data.number).toString() + "등"
            time.text = getTime(data.time)
        }

        private fun getImage(trackId: String) {
            val storageReference = FirebaseStorage.getInstance().getReference("/track/$trackId.png")
            try {
                val localFile: File = File.createTempFile("tempfile", ".png")
                storageReference.getFile(localFile)
                    .addOnSuccessListener {
                        val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                        Glide.with(itemView.context).load(bitmap).into(trackImg)
                    }.addOnFailureListener{
                        Glide.with(itemView.context).load(R.drawable.unknowntrack).into(trackImg)
                    }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        private fun getTime(time: Int): String {
            var min: Int = 0
            var sec: Int = time / 1000
            val mSec: Int = time % 1000
            if (time == 999999) {
                return "-"
            } else {
                while (sec > 60) {
                    sec -= 60
                    min++
                }
                return String.format("%02d:%02d.%03d", min, sec, mSec)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_track, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount() = dataList.size
}