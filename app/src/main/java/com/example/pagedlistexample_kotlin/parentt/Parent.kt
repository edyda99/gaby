package com.example.pagedlistexample_kotlin.parentt
import android.os.Parcel
import android.os.Parcelable

data class  Parent(
    val incomplete_results: Boolean,
    val items: List<Item>?,
    val total_count: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readParcelable(Item::class.java.classLoader),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (incomplete_results) 1 else 0)
        parcel.writeInt(total_count)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Parent> {
        override fun createFromParcel(parcel: Parcel): Parent {
            return Parent(parcel)
        }

        override fun newArray(size: Int): Array<Parent?> {
            return arrayOfNulls(size)
        }
    }
}