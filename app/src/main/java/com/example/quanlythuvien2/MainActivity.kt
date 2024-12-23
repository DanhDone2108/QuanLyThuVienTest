package com.example.quanlythuvien2

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.quanlythuvien2.Adapter.AdapterUser
import com.example.quanlythuvien2.Adapter.OnUserClickListener
import com.example.quanlythuvien2.Model.User
import com.example.quanlythuvien2.View.UserDetailFragment
import com.example.quanlythuvien2.ViewModel.UserViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), OnUserClickListener {

    private lateinit var rcv_user: RecyclerView
    private lateinit var adapterUser: AdapterUser
    private lateinit var linearLayoutManager: LinearLayoutManager

    private val userViewModel: UserViewModel by viewModels()

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

        // Observe user data from ViewModel
        userViewModel.users.observe(this) { userList ->
            adapterUser.setDataUser(userList)
        }

        rcv_user.adapter = adapterUser

        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(adapterUser))
        itemTouchHelper.attachToRecyclerView(rcv_user)

        // FloatingActionButton - Add User
        findViewById<FloatingActionButton>(R.id.btn_add_user).setOnClickListener {
            showAddUserDialog()
        }
    }

    override fun ClickItem(user: User) {
        // Handle item click as before...
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
                val newUser = User(R.drawable.anh_bia_1, name = etUserName.text.toString(), address = etUserAddress.text.toString(), lastname = etUserLastname.text.toString(), Phone = etUserPhone.text.toString(), age = etUserAge.text.toString().toInt())
                userViewModel.addUser(newUser) // Use ViewModel to add user

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
                adapter.removeUserAt(position) // Call method from adapter to remove user.
            }
        }
    }
}




