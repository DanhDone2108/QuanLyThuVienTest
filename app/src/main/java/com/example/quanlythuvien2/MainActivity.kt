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
import com.example.quanlythuvien2.Data.AppDatabase
import com.example.quanlythuvien2.Data.User
import com.example.quanlythuvien2.Data.UserDao
import com.example.quanlythuvien2.ui.theme.QuanLyThuVien2Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var db: AppDatabase
    private lateinit var userDao: UserDao

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = AppDatabase.getDatabase(this)
        userDao = db.userDao()

        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        //val dataSet = arrayOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = CustomAdapter(emptyList())
        recyclerView.adapter = adapter

        CoroutineScope(Dispatchers.IO).launch {
            userDao.insertAll(
                User(1, "John", "Doe"),
                User(2, "Jane", "Doe"),
                User(3, "Alice", "Smith"),
                User(4, "Bob", "Johnson"),
                User(5, "Charlie", "Brown"),
                User(6, "David", "Wilson"),
                User(7, "Eva", "Davis"),
                User(8, "Frank", "Miller"),
                User(9, "Grace", "Lee")
            )

            // Lấy danh sách người dùng từ cơ sở dữ liệu và cập nhật adapter trên UI thread
            val users = userDao.getAll()
            Log.d("MainActivity", "Users fetched: $users") // In ra danh sách người dùng
            launch(Dispatchers.Main) {
                adapter.updateData(users) // Cập nhật dữ liệu cho adapter
            }
        }
//        enableEdgeToEdge()
//        setContent {
//            QuanLyThuVien2Theme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                    UserList(modifier = Modifier.padding(innerPadding), userDao)
//                }
//            }
//        }

    }
}


class CustomAdapter(private var dataSet: List<User>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_user, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val user = dataSet[position]
        viewHolder.textView.text = "${user.firstName} ${user.lastName}" // Hiển thị tên người dùng
    }

    override fun getItemCount() = dataSet.size

    fun updateData(newDataSet: List<User>) {
        dataSet = newDataSet
        notifyDataSetChanged() // Thông báo cho RecyclerView rằng dữ liệu đã thay đổi
    }
}

//@Composable
//fun UserList(modifier: Modifier, userDao: UserDao) {
//
//    val usersState = remember { mutableStateOf(listOf<User>()) }
//    LaunchedEffect(Unit) {
//
//        usersState.value = userDao.getAll() // Lấy danh sách người dùng từ DAO
//    }
//
//    LazyColumn(modifier = modifier) {
//        items(usersState.value) { user ->
//            Text(text = "${user.firstName} ${user.lastName ?: ""}") // Hiển thị tên người dùng
//        }
//    }
//}
//
//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    QuanLyThuVien2Theme {
//        Greeting("Android")
//
//    }
//}

//@Preview(showBackground = true)
//@Composable
//fun PreviewUserList() {
//    // Tạo dữ liệu mẫu
//    val sampleUsers = listOf(
//        User(uid = 1, firstName = "Nguyen", lastName = "Van A"),
//        User(uid = 2, firstName = "Tran", lastName = "Thi B"),
//        User(uid = 3, firstName = "Le", lastName = "Van C")
//    )
//
//    // Sử dụng một UserDao giả lập để cung cấp dữ liệu cho UserList
//    val userDao = object : UserDao {
//        override fun getAll(): List<User> {
//            return sampleUsers // Trả về danh sách người dùng mẫu
//        }
//
//        override fun loadAllByIds(userIds: IntArray): List<User> {
//            return sampleUsers.filter { userIds.contains(it.uid) }
//        }
//
//        override fun findByName(first: String, last: String): User {
//            return sampleUsers.firstOrNull { it.firstName == first && it.lastName == last } ?: throw NoSuchElementException("User not found")
//        }
//
//        override fun insertAll(vararg users: User) {
//
//        }
//
//        override fun delete(user: User) {
//
//        }
//    }
//    QuanLyThuVien2Theme {
//        // Gọi hàm UserList với UserDao giả lập
//        UserList(modifier = Modifier.fillMaxSize(), userDao = userDao)
//    }
//}