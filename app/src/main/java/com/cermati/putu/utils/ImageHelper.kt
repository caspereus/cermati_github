package com.cermati.putu.utils

import android.widget.ImageView
import com.cermati.putu.R
import com.facebook.shimmer.ShimmerFrameLayout
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import timber.log.Timber

object ImageHelper {
    fun getPicasso(
        imageView: ImageView,
        image_url: String?,
        callback: Return? = null,
        loading: ShimmerFrameLayout? = null
    ) {
        Picasso.get().load(image_url)
            .placeholder(R.drawable.img_no_images)
            .error(R.drawable.img_no_images)
            .into(imageView, object : Callback {
                override fun onSuccess() {
                    callback?.onImageLoaded(imageView, loading)
                }

                override fun onError(e: Exception) {
                    Timber.e("Picasso%s", e.message!!)
                    Timber.e("Picasso_URl %s", image_url)
                    callback?.onImageFailed(e.message!!)
                }
            })
    }


    interface Return {
        fun onImageLoaded(
            imageView: ImageView? = null,
            shimmerFrameLayout: ShimmerFrameLayout? = null
        )

        fun onImageFailed(
            error: String,
            shimmerFrameLayout: ShimmerFrameLayout? = null
        )
    }
}