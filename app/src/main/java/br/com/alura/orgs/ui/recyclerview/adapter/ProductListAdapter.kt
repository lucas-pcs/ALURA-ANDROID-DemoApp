package br.com.alura.orgs.ui.recyclerview.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.orgs.databinding.ProductItemBinding
import br.com.alura.orgs.ui.extensions.loadImage
import br.com.alura.orgs.ui.model.Product
import coil.load
import java.text.NumberFormat
import java.util.Locale

class ProductListAdapter(
    private val context: Context,
    var productList: List<Product> = emptyList(),
    var productClickListener: (product: Product) -> Unit = {},
    var productLongClickListener: (product: Product,view: View) -> Unit = {_, _ -> }
) : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    private val productListDataSet = productList.toMutableList()

    inner class ProductViewHolder(productItemBinding: ProductItemBinding) : RecyclerView.ViewHolder(productItemBinding.root) {
        private val nomeTv = productItemBinding.productItemName
        private val descricaoTv= productItemBinding.productItemDescription
        private val valorTv = productItemBinding.productItemValue
        private val imageIv = productItemBinding.productItemImage

        private lateinit var product: Product

        init {
            itemView.setOnClickListener {
                productClickListener(product)
            }
            itemView.setOnLongClickListener {
                productLongClickListener(product, it)
                true
            }
        }

        fun bind(product: Product){
            this.product = product

            nomeTv.text = product.name
            descricaoTv.text = product.description
            val productValueString = NumberFormat.getCurrencyInstance(Locale("pt", "br")).format(product.value)
            valorTv.text = productValueString

            val imageVisibility = if(product.imageURL == null){
                View.GONE
            } else {
                View.VISIBLE
            }

            imageIv.visibility = imageVisibility
            imageIv.loadImage(product.imageURL)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val productItemBinding = ProductItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ProductViewHolder(productItemBinding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productListDataSet[position])
    }

    override fun getItemCount(): Int {
        return productListDataSet.size
    }

    fun refreshList(productList: List<Product>) {
        this.productListDataSet.clear()
        this.productListDataSet.addAll(productList)
        notifyDataSetChanged()
    }

}