package ru.jdeveloperapps.filmnews.other

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import ru.jdeveloperapps.filmnews.R
import java.lang.Exception

@BindingAdapter("android:imageURL")
fun setImageURL(imageView: ImageView, url: String?) {

    try {
        if (!url.isNullOrEmpty()) {
            imageView.alpha = 0.0f
            Picasso.get().load(url).noFade().into(imageView, object : Callback {
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
