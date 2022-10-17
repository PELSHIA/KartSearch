package com.hcraestrak.kartsearch.view.bindingAdapter

import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.hcraestrak.kartsearch.R
import java.io.File
import java.io.IOException
import kotlin.math.roundToInt

object BindingAdapter {

    @JvmStatic
    @BindingAdapter(value = ["imageId", "imageType"])
    fun bindSetImage(view: ImageView, imageId: String, imageType: String) {
        val storageReference = FirebaseStorage.getInstance().getReference("/$imageType/$imageId.png")
        try {
            val localFile: File = File.createTempFile("tempfile", ".png")
            storageReference.getFile(localFile)
                .addOnSuccessListener {
                    val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                    Glide.with(view.context).load(bitmap).into(view)
                }.addOnFailureListener{
                    when (imageType) {
                        "kart" -> Glide.with(view.context).load(R.drawable.unknownkart).into(view)
                        "track" -> Glide.with(view.context).load(R.drawable.unknowntrack).into(view)
                        "character" -> Glide.with(view.context).load(R.drawable.unknowncharacter).into(view)
                    }
                }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["playerCount", "rank"])
    fun bindSetRankText(view: TextView, playerCount: Int, rank: String) {
        if (rank == "" || rank == "99") {
            view.text = "Re"
        } else {
            view.text = "${rank}/${playerCount}"
        }
    }

    @JvmStatic
    @BindingAdapter("setTime")
    fun bindSetTime(view: TextView, time: String) {
        if (time.isNullOrEmpty() || time == "999999") {
            view.text = "-"
        } else {
            val record: Int = time.toInt()
            var min: Int = 0
            var sec: Int = record / 1000
            val mSec: Int = record % 1000
            while (sec > 60) {
                sec -= 60
                min++
            }
            view.text = String.format("%02d:%02d.%03d", min, sec, mSec)
        }
    }

    @JvmStatic
    @BindingAdapter("setTrackName")
    fun bindSetTrackName(view: TextView, trackId: String) {
        val database: DatabaseReference = Firebase.database("https://kartmap.firebaseio.com/").reference
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val id = postSnapshot.child("id").getValue(String::class.java)
                    val name = postSnapshot.child("name").getValue(String::class.java).toString()
                    if (id == trackId) {
                        view.text = name
                        break
                    }
                    view.text = "알 수 없는 트랙"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("error", "${error.code}: ${error.message}")
            }
        })
    }

    @JvmStatic
    @BindingAdapter("setRank")
    fun bindSetRank(view: TextView, rank: String) {
        if (rank == "" || rank == "99") {
            view.text = "Re"
        } else {
            view.text = rank
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["win", "winNumber"])
    fun bindSetWinData(view: TextView, win: String  , winNumber: String) {
        val winNum = win.toInt()
        val num = winNumber.toInt()
        view.text = (winNum.toDouble() / num.toDouble() * 100.0).roundToInt().toString() + "%"
    }

    @JvmStatic
    @BindingAdapter(value = ["avg", "avgNumber"])
    fun bindSetAvgData(view: TextView, avg: String, avgNumber: String) {
        val avgNum = avg.toInt()
        val num = avgNumber.toInt()
        view.text = String.format("%.1f등", avgNum.toDouble() / num.toDouble())
    }

}