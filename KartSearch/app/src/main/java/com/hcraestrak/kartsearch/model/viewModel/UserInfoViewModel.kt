package com.hcraestrak.kartsearch.model.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserInfoViewModel: ViewModel() {

    val mode = MutableLiveData<String>()
    val gameTypeId = MutableLiveData<String>()

    private lateinit var database: DatabaseReference

    fun getGameType(typeName: String) {
        database = Firebase.database("https://gametype.firebaseio.com/").reference
        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val id = postSnapshot.child("id").getValue(String::class.java)
                    val name = postSnapshot.child("name").getValue(String::class.java)
                    if (name == typeName) {
                        gameTypeId.postValue(id)
                        return
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("error", "${error.code}: ${error.message}")
            }
        })
    }
}

