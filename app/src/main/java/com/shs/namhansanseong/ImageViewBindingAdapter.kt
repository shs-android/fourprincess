package com.shs.namhansanseong

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter


@BindingAdapter("srcCompatRes")
fun AppCompatImageView.bindSrcCompat(drawableResId: Int) {
    // Your setter code goes here, like setDrawable or similar
    setImageResource(drawableResId)
}