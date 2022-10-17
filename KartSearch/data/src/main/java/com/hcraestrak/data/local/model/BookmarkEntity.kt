package com.hcraestrak.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmark_Table")
data class BookmarkEntity(
    @PrimaryKey
    @ColumnInfo(name = "nickName")
    val nickName: String
)
