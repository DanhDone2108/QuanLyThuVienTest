package com.example.quanlythuvien2

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity(), OnUserClickListener {

    private lateinit var rcv_user: RecyclerView
    private lateinit var adapterUser: AdapterUser
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)



        rcv_user = findViewById(R.id.rcv_user)
        adapterUser = AdapterUser()
        adapterUser.setOnUserClickListener(this)
        linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rcv_user.layoutManager = linearLayoutManager
        getlistuser()
        rcv_user.adapter = adapterUser

        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(adapterUser))
        itemTouchHelper.attachToRecyclerView(rcv_user)



        // FloatingActionButton - Add User
        findViewById<FloatingActionButton>(R.id.btn_add_user).setOnClickListener {
            showAddUserDialog() // Gọi hàm để thêm người dùng mới
        }

    }

    fun updateToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    private fun getlistuser() {
        CoroutineScope(Dispatchers.IO).launch {
            val userlist = fetchUsersFromDatabase() // Đảm bảo hàm này nhẹ
            runOnUiThread {
                adapterUser.setDataUser(userlist) // Cập nhật UI trên luồng chính
            }
        }
    }

    override fun ClickItem(user: User) {
        // Mở UserDetailFragment thay vì UserDetailActivity
        val fragment = UserDetailFragment()
        val bundle = Bundle().apply {
            putString("USER_NAME", user.name)
            putString("USER_ADDRESS", user.address)
            putString("USER_PHONE", user.Phone)
            putInt("USER_AGE", user.age)
            putString("USER_LASTNAME", user.lastname)
            putInt("USER_IMAGE", user.resourceid)
        }
        fragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment) // Thay thế container với UserDetailFragment
            .addToBackStack(null) // Để có thể quay lại Fragment trước đó
            .commit()
    }



    class SwipeToDeleteCallback(private val adapter: AdapterUser) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                adapter.removeUserAt(position) // Gọi hàm xóa user từ adapter
            }
        }
    }


    private fun showAddUserDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_user, null)
        val etUserName = dialogView.findViewById<EditText>(R.id.et_user_name)
        val etUserAddress = dialogView.findViewById<EditText>(R.id.et_user_address)
        val etUserLastname = dialogView.findViewById<EditText>(R.id.et_user_lastname)
        val etUserPhone = dialogView.findViewById<EditText>(R.id.et_user_phone)
        val etUserAge = dialogView.findViewById<EditText>(R.id.et_user_age)

        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("Thêm người dùng")
            .setView(dialogView)
            .setNegativeButton("Hủy") { dialog, _ -> dialog.dismiss() }
            .setPositiveButton("Thêm") { dialog, _ ->
                // Lấy thông tin từ EditText
                val name = etUserName.text.toString().trim()
                val address = etUserAddress.text.toString().trim()
                val lastname = etUserLastname.text.toString().trim()
                val phone = etUserPhone.text.toString().trim()
                val ageStr = etUserAge.text.toString().trim()

                // Kiểm tra thông tin đầu vào
                if (name.isEmpty() || address.isEmpty() || lastname.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val age = ageStr.toIntOrNull()
                if (age == null || age < 0) {
                    Toast.makeText(this, "Tuổi không hợp lệ", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                // Tạo một đối tượng User mới
                val newUser = User(R.drawable.anh_bia_1, name, address, lastname, phone, age)

                // Cập nhật danh sách người dùng trong adapter
                addUser(newUser)

                Toast.makeText(this, "Người dùng đã được thêm thành công.", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }

        dialogBuilder.create().show()
    }

    private fun addUser(user: User) {
        if (user.name.isBlank() || user.address.isBlank()) {
            // Hiển thị thông báo lỗi nếu thông tin không hợp lệ
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_SHORT).show()
            return
        }
        // Cập nhật danh sách người dùng trong adapter
        val updatedList = adapterUser.getCurrentUsers().toMutableList()
        updatedList.add(user)

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



