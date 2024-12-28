package com.example.quanlythuvien2

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.quanlythuvien2.Activity.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Khởi tạo SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        val editTextUsername = findViewById<EditText>(R.id.editTextUsername)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)

        buttonLogin.setOnClickListener {
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()

            // Kiểm tra thông tin đăng nhập (ví dụ đơn giản)
            if (username == "admin" && password == "admin") {
                // Đăng nhập thành công, lưu trạng thái vào SharedPreferences
                saveLoginStatus(true)

                // Chuyển đến MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Kết thúc LoginActivity để không quay lại được
            } else {
                Toast.makeText(this, "Sai tên đăng nhập hoặc mật khẩu!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveLoginStatus(isLoggedIn: Boolean) {
        // Lưu trạng thái đăng nhập vào SharedPreferences
        with(sharedPreferences.edit()) {
            putBoolean("isLoggedIn", isLoggedIn)
            apply() // Lưu thay đổi
        }
    }
}
