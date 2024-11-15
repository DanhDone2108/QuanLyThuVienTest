package com.example.quanlythuvien2

import android.content.Context
import android.location.Address
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterUser(private val mContext: Context) : RecyclerView.Adapter<AdapterUser.UserViewHolder>() {

    private var mListUser: List<User> = listOf()
    private var mListAddress: List<Address> = listOf()

    fun setDataUser(list: List<User>) {
        this.mListUser = list
        notifyDataSetChanged()
    }

    fun setDataAddress(list: List<Address>) {
        // Nếu bạn cần xử lý địa chỉ, hãy thêm logic ở đây.
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val viewUser = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(viewUser)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = mListUser[position]
        if (user != null) {
            holder.imgUser.setImageResource(user.resourceid)
            holder.tUser.text = user.name
            holder.dAddress.text = user.address
        }
    }

    override fun getItemCount(): Int {
        return mListUser.size
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgUser: ImageView = itemView.findViewById(R.id.img_user)
        val tUser: TextView = itemView.findViewById(R.id.t_user)
        val dAddress: TextView = itemView.findViewById(R.id.d_address)
    }
}