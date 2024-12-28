package com.example.quanlythuvien2.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quanlythuvien2.Model.Book
import com.example.quanlythuvien2.R

class BookAdapter(private val books: List<Book>) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.tv_book_title)
        val authorTextView: TextView = itemView.findViewById(R.id.tv_book_author)
        val yearTextView: TextView = itemView.findViewById(R.id.tv_book_year)
        val imageView: ImageView = itemView.findViewById(R.id.img_book) // Thêm ImageView cho hình ảnh
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.titleTextView.text = book.title
        holder.authorTextView.text = book.author
        holder.yearTextView.text = book.year.toString()
        holder.imageView.setImageResource(book.image) // Thiết lập hình ảnh cho ImageView
    }

    override fun getItemCount(): Int {
        return books.size
    }
}
