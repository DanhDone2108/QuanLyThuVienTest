package com.example.quanlythuvien2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quanlythuvien2.Adapter.BookAdapter
import com.example.quanlythuvien2.Model.Book

class BookListActivity : AppCompatActivity() {

    private lateinit var rcv_books: RecyclerView
    private lateinit var bookAdapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)

        rcv_books = findViewById(R.id.rcv_books)

        // Danh sách sách với tên và tác giả khác nhau, kèm theo ID hình ảnh.
        val bookList = listOf(
            Book(R.drawable.hs_1, "Sách 1", "Tác giả A", 2020),
            Book(R.drawable.hs_2, "Sách 2", "Tác giả B", 2019),
            Book(R.drawable.hs_3, "Sách 3", "Tác giả C", 2021),
            // Thêm các cuốn sách khác với ID hình ảnh tương ứng...
        )

        bookAdapter = BookAdapter(bookList) // Khởi tạo adapter với danh sách sách

        rcv_books.layoutManager = LinearLayoutManager(this)
        rcv_books.adapter = bookAdapter // Thiết lập adapter cho RecyclerView
    }
}
