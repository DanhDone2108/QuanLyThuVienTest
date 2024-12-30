package com.example.quanlythuvien2

import BookViewModel
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quanlythuvien2.Adapter.BookAdapter
import com.example.quanlythuvien2.Model.Book


class BookListActivity : AppCompatActivity() {

    private lateinit var rcv_books: RecyclerView
    private lateinit var bookAdapter: BookAdapter
    private val bookViewModel: BookViewModel by viewModels() // Khai báo ViewModel cho sách

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)

        rcv_books = findViewById(R.id.rcv_books)

        bookAdapter = BookAdapter(listOf()) // Khởi tạo với danh sách rỗng

        rcv_books.layoutManager = LinearLayoutManager(this)
        rcv_books.adapter = bookAdapter

        // Quan sát dữ liệu từ ViewModel
        bookViewModel.books.observe(this) { bookList ->
            bookAdapter.setDataBook(bookList) // Cập nhật adapter với danh sách sách mới
        }

        // Thiết lập listener cho sự kiện nhấn vào mục sách
        bookAdapter.setOnBookClickListener { book ->
            val intent = Intent(this, DetailBookActivity::class.java).apply {
                putExtra("book", book) // Truyền đối tượng Book qua Intent
            }
            startActivity(intent) // Khởi chạy DetailBookActivity
        }
    }
}
