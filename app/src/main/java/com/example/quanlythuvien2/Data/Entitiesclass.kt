package com.example.quanlythuvien2.Data

import android.location.Address
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User1(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "first_name")
    val firstName: String?,
    @ColumnInfo(name = "last_name")
    val lastname: String?,
    @ColumnInfo(name = "Address_User")
    val address: String?
)
