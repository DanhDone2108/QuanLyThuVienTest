package com.example.quanlythuvien2.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginVM : ViewModel() {
    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> get() = _loginResult

    fun login(username: String, password: String) {
        // Kiem tra thông tin dang nhap voi du lieu dang nhap (ví dụ: admin/admin)
        _loginResult.value = (username == "admin" && password == "admin")
    }
}
