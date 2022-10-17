package com.hcraestrak.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_Table")
data class SearchEntity(
    @PrimaryKey
    @ColumnInfo(name = "word")
    val word: String
)
