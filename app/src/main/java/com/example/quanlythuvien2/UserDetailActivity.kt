package com.example.quanlythuvien2


import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity

class UserDetailActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        // Nhận dữ liệu từ Intent
        val userName = intent.getStringExtra("USER_NAME")
        val userAddress = intent.getStringExtra("USER_ADDRESS")
        val userPhone = intent.getStringExtra("USER_PHONE")
        val userAge = intent.getIntExtra("USER_AGE", 0)
        val userLastname = intent.getStringExtra("USER_LASTNAME")
        val userImage = intent.getIntExtra("USER_IMAGE", 0)

        // Hiển thị thông tin người dùng
        findViewById<TextView>(R.id.t_user).text = userName
        findViewById<TextView>(R.id.t_lastname).text = userLastname
        findViewById<TextView>(R.id.d_address).text = userAddress
        findViewById<TextView>(R.id.d_phone).text = userPhone
        findViewById<TextView>(R.id.d_age).text = "$userAge"
        findViewById<ImageView>(R.id.img_user).setImageResource(userImage)
    }

}