package com.shs.namhansanseong

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Place(
    val id: Long,
    val name: String,
    val description: String,
    val image: Int,
    val lng: Double, // 경도
    val lat: Double // 위도
): Parcelable