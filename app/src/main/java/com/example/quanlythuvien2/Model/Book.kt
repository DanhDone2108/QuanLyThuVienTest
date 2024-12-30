package com.example.quanlythuvien2.Model

import android.os.Parcel
import android.os.Parcelable

data class Book(
    val image: Int,
    val title: String,
    val author1: String,
    val year: Int,
    val author2: String,
    val description: String // Thêm thuộc tính mô tả
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "" // Đảm bảo đọc mô tả từ Parcel
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(image)
        parcel.writeString(title)
        parcel.writeString(author1)
        parcel.writeInt(year)
        parcel.writeString(author2)
        parcel.writeString(description) // Ghi mô tả vào Parcel
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }
}
