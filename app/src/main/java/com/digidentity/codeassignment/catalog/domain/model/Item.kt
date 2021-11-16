package com.digidentity.codeassignment.catalog.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.Gson

data class Item(val id: String, val text: String, val image: String?, val confidence: Double) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(text)
        parcel.writeString(image)
        parcel.writeDouble(confidence)
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


fun Item.toJson(): String =
    Gson().toJson(this)