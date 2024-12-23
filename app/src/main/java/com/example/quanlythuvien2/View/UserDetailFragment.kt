package com.example.quanlythuvien2.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.quanlythuvien2.R

class UserDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Nhận dữ liệu từ arguments
        val userName = arguments?.getString("USER_NAME")
        val userAddress = arguments?.getString("USER_ADDRESS")
        val userPhone = arguments?.getString("USER_PHONE")
        val userAge = arguments?.getInt("USER_AGE", 0)
        val userLastname = arguments?.getString("USER_LASTNAME")
        val userImage = arguments?.getInt("USER_IMAGE", 0)

        // Hiển thị thông tin người dùng
        view.findViewById<TextView>(R.id.t_user).text = userName
        view.findViewById<TextView>(R.id.t_lastname).text = userLastname
        view.findViewById<TextView>(R.id.d_address).text = userAddress
        view.findViewById<TextView>(R.id.d_phone).text = userPhone
        view.findViewById<TextView>(R.id.d_age).text = "$userAge"

        // Cập nhật hình ảnh người dùng (nếu có)
        if (userImage != null) {
            view.findViewById<ImageView>(R.id.img_user).setImageResource(userImage)
        }
    }
}
