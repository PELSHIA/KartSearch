package com.hcraestrak.kartsearch.model.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_Table")
data class Search(
    @PrimaryKey
    @ColumnInfo(name = "word")
    val word: String
)
