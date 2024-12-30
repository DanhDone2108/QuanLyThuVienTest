package com.example.quanlythuvien2.Activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quanlythuvien2.Adapter.AdapterUser
import com.example.quanlythuvien2.Adapter.OnUserClickListener
import com.example.quanlythuvien2.BookListActivity
import com.example.quanlythuvien2.LoginActivity
import com.example.quanlythuvien2.Model.User
import com.example.quanlythuvien2.R
import com.example.quanlythuvien2.View.AddUserDialog
import com.example.quanlythuvien2.View.UserDetailActivity
import com.example.quanlythuvien2.ViewModel.UserViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), OnUserClickListener {

    private lateinit var rcv_user: RecyclerView
    private lateinit var adapterUser: AdapterUser
    private lateinit var drawerLayout: DrawerLayout

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Kiểm tra trạng thái đăng nhập trước khi thiết lập giao diện chính.
        if (!isLoggedIn()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_main)

        // Thiết lập Toolbar và Navigation Drawer.
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)

        // Khởi tạo RecyclerView và Adapter.
        rcv_user = findViewById(R.id.rcv_user)
        adapterUser = AdapterUser()
        adapterUser.setOnUserClickListener(this)

        rcv_user.layoutManager = LinearLayoutManager(this)
        rcv_user.adapter = adapterUser

        // Quan sát dữ liệu người dùng từ ViewModel.
        userViewModel.users.observe(this) { userList ->
            adapterUser.setDataUser(userList)
        }

        // Thiết lập Swipe to Delete cho RecyclerView.
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(adapterUser))
        itemTouchHelper.attachToRecyclerView(rcv_user)

        // FloatingActionButton - Thêm người dùng mới.
        findViewById<FloatingActionButton>(R.id.btn_add_user).setOnClickListener {
            showAddUserDialog()
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_users -> {
                    Toast.makeText(this, "Đang xem Người dùng", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_books -> {
                    startActivity(Intent(this, BookListActivity::class.java))
                }
            }
            drawerLayout.closeDrawers() // Đóng Navigation Drawer sau khi chọn mục.
            true // Trả về true để đánh dấu rằng sự kiện đã được xử lý.
        }
    }

    private fun isLoggedIn(): Boolean {
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    override fun ClickItem(user: User) {
        val intent = Intent(this, UserDetailActivity::class.java).apply {
            putExtra("USER_NAME", user.name)
            putExtra("USER_LASTNAME", user.lastname)
            putExtra("USER_ADDRESS", user.address)
            putExtra("USER_PHONE", user.Phone)
            putExtra("USER_AGE", user.age)
            putExtra("USER_IMAGE", user.resourceid)
        }
        startActivity(intent)
    }

    private fun showAddUserDialog() {
        val addUserDialog = AddUserDialog(this, userViewModel)
        addUserDialog.show() // Gọi phương thức show của AddUserDialog để hiển thị hộp thoại.
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.drawer_menu, menu) // Inflate menu từ XML (đảm bảo tên file đúng).
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_users -> {
                Toast.makeText(this, "Đang xem Người dùng", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.nav_books -> {
                startActivity(Intent(this, BookListActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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
