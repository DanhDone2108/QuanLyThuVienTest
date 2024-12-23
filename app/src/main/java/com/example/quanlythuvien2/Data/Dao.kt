package com.example.quanlythuvien2.Data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User1::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao


}

@Dao
interface UserDao {
    @Query("SELECT * FROM user1")
    fun getAll(): List<User1>

    @Query("SELECT * FROM user1 WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User1>

    @Query("SELECT * FROM user1 WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): User1

    @Query("SELECT * FROM user1 WHERE Address_User = :address")
    fun findByAddress(address: String): List<User1>

    @Insert
    fun insertAll(vararg users: User1)

    @Delete
    fun delete(user: User1)
}