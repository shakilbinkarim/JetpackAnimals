package io.github.shakilbinkarim.jetpackanimals.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import androidx.palette.graphics.Palette
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import io.github.shakilbinkarim.jetpackanimals.R

fun getProgressDrawable(context: Context) = CircularProgressDrawable(context).apply {
    strokeWidth = 10f
    centerRadius = 50f
    start()
}

fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable){
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher_round)
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}

@BindingAdapter("android:imageUrl")
fun loadImage(view: ImageView, url: String?){
    view.loadImage(url, getProgressDrawable(view.context))
}

@BindingAdapter("android:background")
fun setUpBackgroundColor(view: LinearLayout, url: String?) {
    Glide.with(view.context)
        .asBitmap()
        .load(url)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                Palette.from(resource)
                    .generate { palette ->
                        val color = palette?.lightMutedSwatch?.rgb ?: 0
                        view.setBackgroundColor(color)
                    }
            }
            override fun onLoadCleared(placeholder: Drawable?) {
            }
        })
}

