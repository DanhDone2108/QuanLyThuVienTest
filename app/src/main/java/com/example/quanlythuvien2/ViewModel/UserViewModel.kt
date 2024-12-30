package com.example.quanlythuvien2.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quanlythuvien2.Model.User
import com.example.quanlythuvien2.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    init {
        fetchUsersFromDatabase()
    }

    private fun fetchUsersFromDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            // Giả lập tải dữ liệu từ API hoặc cơ sở dữ liệu
            val userList = listOf(
                User(R.drawable.anh_bia_1, "Name User: 1", "Address 1", "Last Name: 1", "1234567890", 25),
                User(R.drawable.anh_bia_2, "Name User: 2", "Address 2", "Last Name: 2", "0987654321", 30),
                User(R.drawable.anh_bia_3, "Name User: 3", "Address 3", "Last Name: 3", "1122334455", 22),
                User(R.drawable.anh_bia_4, "Name User: 4", "Address 4", "Last Name: 4", "2233445566", 28)
            )
            _users.postValue(userList) // Cập nhật LiveData trên Main Thread
        }
    }

    fun addUser(user: User) {
        val currentList = _users.value?.toMutableList() ?: mutableListOf()
        currentList.add(user)
        _users.value = currentList // Cập nhật LiveData
    }

    fun removeUserAt(position: Int) {
        val currentList = _users.value?.toMutableList() ?: return
        if (position in currentList.indices) {
            currentList.removeAt(position)
            _users.value = currentList // Cập nhật LiveData
        }
    }
}
