package com.example.quanlythuvien2

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.util.newStringBuilder
import com.example.quanlythuvien2.Data.AppDatabase
import com.example.quanlythuvien2.Data.UserDao
import com.example.quanlythuvien2.ui.theme.QuanLyThuVien2Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext

class MainActivity : ComponentActivity() {

    private lateinit var rcv_user: RecyclerView
    private lateinit var adapterUser: AdapterUser
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rcv_user = findViewById(R.id.rcv_user)
        adapterUser = AdapterUser(this)

        linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rcv_user.layoutManager = linearLayoutManager
        getlistuser()
        rcv_user.adapter = adapterUser
    }


    private fun getlistuser() {
        CoroutineScope(Dispatchers.IO).launch {
            val userlist = fetchUsersFromDatabase()
            runOnUiThread{
                adapterUser.setDataUser(userlist)
            }
        }
    }

    private fun fetchUsersFromDatabase(): List<User> {
        val users = mutableListOf<User>()
        val images = listOf(
            R.drawable.anh_bia_1,
            R.drawable.anh_bia_2,
            R.drawable.anh_bia_3,
            R.drawable.anh_bia_4
        )

        val names = listOf(
            "Name User 1",
            "Name User 2",
            "Name User 3",
            "Name User 4"
        )

        val addresses = listOf(
            "Address 1",
            "Address 2",
            "Address 3",
            "Address 4"
        )

        // Lặp qua danh sách hình ảnh và tên để thêm vào danh sách người dùng
        for (i in images.indices) {
            users.add(User(images[i], names[i % names.size], addresses[i % addresses.size]))
        }

        return users
    }
}



