package com.example.quanlythuvien2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import com.example.quanlythuvien2.Model.Book

class DetailBookActivity : AppCompatActivity() {

    private lateinit var imgBookDetail: ImageView
    private lateinit var tvBookTitleDetail: TextView
    private lateinit var tvBookAuthorDetail1: TextView
    private lateinit var tvBookAuthorDetail2: TextView
    private lateinit var tvBookYearDetail: TextView
    private lateinit var tvBookDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_book)

        imgBookDetail = findViewById(R.id.img_book_detail)
        tvBookTitleDetail = findViewById(R.id.tv_book_title_detail)
        tvBookAuthorDetail1 = findViewById(R.id.tv_book_author_detail_1)
        tvBookAuthorDetail2 = findViewById(R.id.tv_book_author_detail_2)
        tvBookYearDetail = findViewById(R.id.tv_book_year_detail)
        tvBookDescription = findViewById(R.id.tv_book_description)

        // Nhận dữ liệu từ Intent
        val book = intent.getParcelableExtra<Book>("book")

        // Kiểm tra xem book có khác null không và hiển thị thông tin
        if (book != null) {
            imgBookDetail.setImageResource(book.image) // Hiển thị hình ảnh bìa sách
            tvBookTitleDetail.text = book.title // Tiêu đề sách
            tvBookAuthorDetail1.text = book.author1 // Tác giả chính
            tvBookAuthorDetail2.text = book.author2 // Tác giả phụ
            tvBookYearDetail.text = book.year.toString() // Năm xuất bản
            tvBookDescription.text = book.description // Hiển thị mô tả sách
        } else {
            Log.e("DetailBookActivity", "No book data received") // Log lỗi nếu không nhận được dữ liệu
        }
    }
}
