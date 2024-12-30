package com.example.quanlythuvien2.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quanlythuvien2.DetailBookActivity
import com.example.quanlythuvien2.Model.Book
import com.example.quanlythuvien2.R

class BookAdapter(private var books: List<Book>) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    private var onBookClickListener: ((Book) -> Unit)? = null

    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.tv_book_title)
        val authorTextView: TextView = itemView.findViewById(R.id.tv_book_author)
        val yearTextView: TextView = itemView.findViewById(R.id.tv_book_year)
        val imageView: ImageView = itemView.findViewById(R.id.img_book)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.titleTextView.text = book.title
        holder.authorTextView.text = book.author1
        holder.yearTextView.text = book.year.toString()
        holder.imageView.setImageResource(book.image)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailBookActivity::class.java)
            intent.putExtra("book", book) // Truyền đối tượng Book qua Intent (đảm bảo Book implements Parcelable)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return books.size
    }

    fun setOnBookClickListener(listener: (Book) -> Unit) {
        this.onBookClickListener = listener // Thiết lập listener cho sự kiện nhấn vào sách
    }

    fun setDataBook(bookList: List<Book>) {
        this.books = bookList // Cập nhật danh sách sách
        notifyDataSetChanged() // Thông báo cho adapter biết rằng dữ liệu đã thay đổi
    }
}
