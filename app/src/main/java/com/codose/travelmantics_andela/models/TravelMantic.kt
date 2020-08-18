package com.codose.travelmantics_andela.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TravelMantic(
    var id : String = "",
    var placeName : String = "",
    var description: String = "",
    var imageUrl : String = ""
) : Parcelable