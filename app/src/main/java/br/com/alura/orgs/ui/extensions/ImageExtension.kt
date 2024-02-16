package br.com.alura.orgs.ui.extensions

import android.os.Build.VERSION.SDK_INT
import android.widget.ImageView
import br.com.alura.orgs.R
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load

fun ImageView.loadImage(imageURL: String?){
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    load(imageURL, imageLoader){
        fallback(R.drawable.error_image_loading)
        error(R.drawable.error_image_loading)
        placeholder(R.drawable.placeholder_image_loading)
    }
}