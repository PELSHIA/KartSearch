package com.hcraestrak.kartsearch.model.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmark_Table")
data class Bookmark(
    @PrimaryKey
    @ColumnInfo(name = "nickName")
    val nickName: String
)
