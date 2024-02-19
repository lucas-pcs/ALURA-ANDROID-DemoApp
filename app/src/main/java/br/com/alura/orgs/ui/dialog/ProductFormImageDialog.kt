package br.com.alura.orgs.ui.dialog

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import br.com.alura.orgs.databinding.ProductFormImageloadBinding
import br.com.alura.orgs.ui.extensions.loadImage

class ProductFormImageDialog(private val context: Context) {
    private var url: String? = null
    fun showImageDialog(previousImageURL: String? ,imageLoadingFinished: (imageURL: String?) -> Unit){
        val productFormImageloadBinding = ProductFormImageloadBinding.inflate(LayoutInflater.from(context))

        productFormImageloadBinding.productFormImageloadImage.loadImage(previousImageURL)

        productFormImageloadBinding.productFormImageloadButton.setOnClickListener {

            url = productFormImageloadBinding.productFormImageloadTextinputedittextUrl.text.toString()
            productFormImageloadBinding.productFormImageloadImage.loadImage(url)
        }


        AlertDialog.Builder(context)
            .setView(productFormImageloadBinding.root)
            .setPositiveButton("Confirm") { _, _ ->
                imageLoadingFinished(url)
            }
            .setNegativeButton("Cancel") { _, _->}
            .show()

    }
}