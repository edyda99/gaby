package com.example.pagedlistexample_kotlin.parentt

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.databinding.BaseObservable
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

//@Entity
data class Item(
    @PrimaryKey
    @NonNull
    @SerializedName("id")
    val id: Int=0,
    @SerializedName("node_id")
    val node_id: String?,
    @SerializedName("private")
    val pri: Boolean,
    @SerializedName("name")
    val name: String?,
    @SerializedName("full_name")
    val full_name: String?,
    @SerializedName("owner")
    val owner: Owner
) : BaseObservable(), Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Owner::class.java.classLoader)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(node_id)
        parcel.writeByte(if (pri) 1 else 0)
        parcel.writeString(name)
        parcel.writeString(full_name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(parcel: Parcel): Item {
            return Item(parcel)
        }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }
    }
}