package com.hcraestrak.kartsearch.view.adapter

import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
import com.hcraestrak.kartsearch.view.adapter.data.UserInfoData
import java.io.File
import java.io.IOException

class UserInfoRecyclerViewAdapter: RecyclerView.Adapter<UserInfoRecyclerViewAdapter.ViewHolder>() {

    private val data = mutableListOf<UserInfoData>()

    fun setData(data: List<UserInfoData>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rank: TextView = view.findViewById(R.id.item_rank)
        val kart: ImageView = view.findViewById(R.id.item_kart_img)
        val map: TextView = view.findViewById(R.id.item_map)
        val time: TextView = view.findViewById(R.id.item_time)

        fun bind(data: UserInfoData) {
            when (data.isRetired) {
                "0" -> {
                    rank.text = "${data.userRank}/${data.playerCount}"
                    time.text = data.time
                }
                "1" -> {
                    rank.text = "Re"
                    time.text = "-"
                }
            }

            when (data.isWin) {
                "0" -> {
                    itemView.setBackgroundResource(R.drawable.background_lose)
                    rank.setTextColor(Color.parseColor("#FF8484"))
                }
                "1" -> {
                    itemView.setBackgroundResource(R.drawable.background_win)
                    rank.setTextColor(Color.parseColor("#7CA8FF"))
                }
            }
            getImage(data.kart)
            getTrackName(data.track, map)
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

        private fun getTrackName(trackId: String, view: TextView) {
            val database: DatabaseReference = Firebase.database.reference
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (postSnapshot in snapshot.children) {
                        val id = postSnapshot.child("id").getValue(String::class.java)
                        val name = postSnapshot.child("name").getValue(String::class.java).toString()
                        if (id == trackId) {
                            view.text = name
                            break
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("error", "${error.code}: ${error.message}")
                }
            })
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