package com.example.quanlythuvien2.View

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import com.example.quanlythuvien2.Model.User
import com.example.quanlythuvien2.R
import com.example.quanlythuvien2.ViewModel.UserViewModel

class AddUserDialog(private val context: Context, private val userViewModel: UserViewModel) {

    fun show() {
        // Tạo một View cho hộp thoại
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_user, null)

        // Tạo AlertDialog
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setView(dialogView)
        dialogBuilder.setTitle("Thêm người dùng")

        // Khởi tạo các EditText từ dialogView
        val etUserName = dialogView.findViewById<EditText>(R.id.et_user_name)
        val etUserLastname = dialogView.findViewById<EditText>(R.id.et_user_lastname)
        val etUserAddress = dialogView.findViewById<EditText>(R.id.et_user_address)
        val etUserPhone = dialogView.findViewById<EditText>(R.id.et_user_phone)
        val etUserAge = dialogView.findViewById<EditText>(R.id.et_user_age)

        // Thiết lập nút "Thêm người dùng" trong hộp thoại
        dialogBuilder.setPositiveButton("Thêm") { _, _ ->
            // Lấy dữ liệu từ các EditText
            val userName = etUserName.text.toString()
            val userLastname = etUserLastname.text.toString()
            val userAddress = etUserAddress.text.toString()
            val userPhone = etUserPhone.text.toString()
            val userAge = etUserAge.text.toString().toIntOrNull() ?: 0

            // Tạo một User mới và thêm vào ViewModel
            if (userName.isNotEmpty() && userLastname.isNotEmpty() && userAddress.isNotEmpty() && userPhone.isNotEmpty()) {
                val newUser = User(
                    resourceid = R.drawable.anh_bia_3, // Thay bằng hình ảnh mặc định hoặc chọn hình ảnh từ người dùng
                    name = userName,
                    lastname = userLastname,
                    address = userAddress,
                    Phone = userPhone,
                    age = userAge
                )
                userViewModel.addUser(newUser) // Thêm User vào ViewModel
                Toast.makeText(context, "Người dùng đã được thêm", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }

        dialogBuilder.setNegativeButton("Hủy") { dialog, _ ->
            dialog.dismiss()
        }

        // Hiển thị hộp thoại
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }
}
