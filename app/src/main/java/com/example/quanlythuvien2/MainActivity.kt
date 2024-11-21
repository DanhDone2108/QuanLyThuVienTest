package com.example.quanlythuvien2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

        findViewById<Button>(R.id.btn_add_user)?.setOnClickListener {
            addUser() // Gọi hàm để thêm người dùng mới
        }
    }



    private fun getlistuser() {
        CoroutineScope(Dispatchers.IO).launch {
            val userlist = fetchUsersFromDatabase()
            runOnUiThread{
                adapterUser.setDataUser(userlist)
            }
        }
    }

    fun ClickItem(user: User) {
        // Mở trang thông tin chi tiết của người dùng
        val intent = Intent(this, UserDetailActivity::class.java).apply {
            putExtra("USER_NAME", user.name)
            putExtra("USER_ADDRESS", user.address)
            putExtra("USER_PHONE", user.Phone)
            putExtra("USER_AGE", user.age)
            putExtra("USER_LASTNAME", user.lastname)
            putExtra("USER_IMAGE", user.resourceid)
        }
        startActivity(intent)
    }

    private fun addUser() {
        // Tạo một người dùng mới và thêm vào danh sách
        val newUser = User(R.drawable.anh_bia_1, "New User", "New Address", "New Lastname", "0000000000", 20)

        // Cập nhật danh sách người dùng trong adapter
        val updatedList = adapterUser.getCurrentUsers().toMutableList()
        updatedList.add(newUser)

        // Cập nhật adapter với danh sách mới
        adapterUser.setDataUser(updatedList)
    }

    private fun fetchUsersFromDatabase(): List<User> {
        return listOf(
            User(R.drawable.anh_bia_1, "Name User: 1", "Address 1", "Last Name: 1", "1234567890", 25),
            User(R.drawable.anh_bia_2, "Name User: 2", "Address 2", "Last Name: 2", "0987654321", 30),
            User(R.drawable.anh_bia_3, "Name User: 3", "Address 3", "Last Name: 3", "1122334455", 22),
            User(R.drawable.anh_bia_4, "Name User: 4", "Address 4", "Last Name: 4", "2233445566", 28)
        )
    }
}



