package dev.rivu.composeclass1.ui

import android.os.Parcel
import android.os.Parcelable

data class Parameter(
    val parameter: String
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString() ?: "") {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(parameter)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Parameter> {
        override fun createFromParcel(parcel: Parcel): Parameter {
            return Parameter(parcel)
        }

        override fun newArray(size: Int): Array<Parameter?> {
            return arrayOfNulls(size)
        }
    }
}
