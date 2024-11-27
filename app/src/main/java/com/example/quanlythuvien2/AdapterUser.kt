package com.example.quanlythuvien2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class AdapterUser : RecyclerView.Adapter<AdapterUser.UserViewHolder>() {

    private var mListUser: MutableList<User> = mutableListOf()
    private var listener: OnUserClickListener? = null // Khai báo listener

    // Phương thức để thiết lập listener
    fun setOnUserClickListener(listener: OnUserClickListener) {
        this.listener = listener
    }

    fun setDataUser(list: List<User>) {
        this.mListUser.clear()
        this.mListUser.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val viewUser = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(viewUser)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = mListUser[position]

        holder.imgUser.setImageResource(user.resourceid)
        holder.tUser.text = user.name
        holder.dAddress.text = user.address

        // Thiết lập sự kiện nhấn vào mục
        holder.itemView.setOnClickListener {
            listener?.ClickItem(user) // Gọi phương thức từ listener
        }
    }

    override fun getItemCount(): Int {
        return mListUser.size
    }

    fun getCurrentUsers(): List<User> {
        return mListUser // Trả về danh sách người dùng hiện tại
    }

    fun removeUserAt(position: Int) {
        if (position >= 0 && position < mListUser.size) {
            mListUser.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgUser: ImageView = itemView.findViewById(R.id.img_user)
        val tUser: TextView = itemView.findViewById(R.id.t_user)
        val dAddress: TextView = itemView.findViewById(R.id.d_address)
    }
}

interface OnUserClickListener {
    fun ClickItem(user: User)
}
