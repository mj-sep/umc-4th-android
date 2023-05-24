package com.example.week5mission

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Memo (
    @ColumnInfo(name = "contents") val contents: String,
    @ColumnInfo(name = "likes") val likes: Boolean,
    @PrimaryKey (autoGenerate = true) @ColumnInfo(name = "contentsId") val contentsId: Int = 0
)


