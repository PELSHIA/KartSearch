package com.hcraestrak.kartsearch.model.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseViewModel: ViewModel() {

    private lateinit var database: DatabaseReference

    fun getGameType(typeName: String): LiveData<String> {
        val data = MutableLiveData<String>()
        database = Firebase.database("https://gametype.firebaseio.com/").reference
        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val id = postSnapshot.child("id").getValue(String::class.java)
                    val name = postSnapshot.child("name").getValue(String::class.java)
                    if (name == typeName) {
                        data.postValue(id)
                        return
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("error", "${error.code}: ${error.message}")
            }
        })
        return data
    }
}

