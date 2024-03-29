package com.d121211063.mygithubusers.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user")
data class UserFavorite (
    @PrimaryKey @ColumnInfo(name = "login") val login: String,
    @ColumnInfo(name = "avatar_url") val avatar_url: String,
    @ColumnInfo(name = "type") val type: String
) : Serializable