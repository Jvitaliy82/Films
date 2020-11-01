package ru.jdeveloperapps.filmnews.other

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("android:imageURL")
fun setImageURL(imageView: ImageView, url: String) {
    Glide.with(imageView).load(url).into(imageView)
}
