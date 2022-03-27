package com.example.doomscrollingforgodot.doomscroller

import android.widget.ImageView
import androidx.databinding.BindingAdapter


@BindingAdapter("android:translationX")
fun ImageView.setAlpha(xParam: Float?){
    xParam?.let {
        translationX = xParam
    }
}

@BindingAdapter("scale")
fun ImageView.setScale(scaleParam: Float?){
    scaleParam?.let {
        scaleX = scaleParam
        scaleY = scaleParam
    }
}

@BindingAdapter("imageSrc")
fun ImageView.setSrc(srcId: Int?){
    srcId?.let {
        setImageResource(srcId)
    }
}



