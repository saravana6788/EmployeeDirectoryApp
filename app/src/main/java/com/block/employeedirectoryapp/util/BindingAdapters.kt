package com.block.employeedirectoryapp.util

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("thumbnailImage")
fun thumbnailImage(imageView: ImageView, imageUrl: String) {
    val context = imageView.context
    val picasso:Picasso =  Picasso.with(context)
    picasso.setIndicatorsEnabled(true)  // indicator set on image to differentiate between images fetched from Network(RED), Memory(GREEN) and Disk(BLUE)
    picasso.load(imageUrl.toUri().buildUpon().scheme("https").build())
        .fit()
        .centerCrop()
        .into(imageView)
}


