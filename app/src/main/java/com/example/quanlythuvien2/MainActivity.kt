package com.example.quanlythuvien2

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quanlythuvien2.Adapter.AdapterUser
import com.example.quanlythuvien2.Adapter.OnUserClickListener
import com.example.quanlythuvien2.Model.User
import com.example.quanlythuvien2.ViewModel.UserViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), OnUserClickListener {

    private lateinit var rcv_user: RecyclerView
    private lateinit var adapterUser: AdapterUser

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Kiểm tra trạng thái đăng nhập trước khi thiết lập giao diện chính
        if (!isLoggedIn()) {
            // Nếu chưa đăng nhập, chuyển đến LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
            finish() // Kết thúc MainActivity để không quay lại được
            return // Dừng thực hiện tiếp trong onCreate()
        }

        setContentView(R.layout.activity_main)

        // Thiết lập Toolbar
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Khởi tạo RecyclerView và Adapter
        rcv_user = findViewById(R.id.rcv_user)
        adapterUser = AdapterUser()
        adapterUser.setOnUserClickListener(this)

        rcv_user.layoutManager = LinearLayoutManager(this)
        rcv_user.adapter = adapterUser

        // Observe user data from ViewModel
        userViewModel.users.observe(this) { userList ->
            adapterUser.setDataUser(userList)
        }

        // Thiết lập Swipe to Delete cho RecyclerView
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(adapterUser))
        itemTouchHelper.attachToRecyclerView(rcv_user)

        // FloatingActionButton - Thêm người dùng mới
        findViewById<FloatingActionButton>(R.id.btn_add_user).setOnClickListener {
            showAddUserDialog()
        }
    }

    private fun isLoggedIn(): Boolean {
        // Kiểm tra trạng thái đăng nhập từ SharedPreferences.
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    override fun ClickItem(user: User) {
        // Xử lý sự kiện khi người dùng nhấn vào một mục trong danh sách.
    }

    private fun showAddUserDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_user, null)

        val etUserName = dialogView.findViewById<EditText>(R.id.et_user_name)
        val etUserAddress = dialogView.findViewById<EditText>(R.id.et_user_address)
        val etUserLastname = dialogView.findViewById<EditText>(R.id.et_user_lastname)
        val etUserPhone = dialogView.findViewById<EditText>(R.id.et_user_phone)
        val etUserAge = dialogView.findViewById<EditText>(R.id.et_user_age)

        val dialogBuilder = AlertDialog.Builder(this)
            .setTitle("Thêm người dùng")
            .setView(dialogView)
            .setNegativeButton("Hủy") { dialog, _ -> dialog.dismiss() }
            .setPositiveButton("Thêm") { dialog, _ ->
                // Tạo đối tượng người dùng mới từ dữ liệu nhập vào.
                val newUser = User(
                    R.drawable.anh_bia_1,
                    name = etUserName.text.toString(),
                    address = etUserAddress.text.toString(),
                    lastname = etUserLastname.text.toString(),
                    Phone = etUserPhone.text.toString(),
                    age = etUserAge.text.toString().toInt()
                )
                userViewModel.addUser(newUser) // Sử dụng ViewModel để thêm người dùng.

                Toast.makeText(this, "Người dùng đã được thêm thành công.", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }

        dialogBuilder.create().show()
    }

    class SwipeToDeleteCallback(private val adapter: AdapterUser) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                adapter.removeUserAt(position) // Gọi phương thức từ adapter để xóa người dùng.
            }
        }
    }
}
