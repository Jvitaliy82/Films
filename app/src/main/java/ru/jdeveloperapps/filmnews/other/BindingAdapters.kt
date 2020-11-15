package ru.jdeveloperapps.filmnews.other

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

@BindingAdapter("android:imageURL")
fun setImageURL(imageView: ImageView, url: String?) {

    try {
        if (!url.isNullOrEmpty()) {
            imageView.alpha = 0.0f
            Picasso.get().load(url).into(imageView, object : Callback {
                override fun onSuccess() {
                    imageView.animate().setDuration(300).alpha(1f).start()
                }

                override fun onError(e: Exception?) {

                }

            })
        }
    } catch (ignored: Exception) {
    }
}
