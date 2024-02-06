package br.com.alura.orgs.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.com.alura.orgs.R
import br.com.alura.orgs.databinding.ProductItemBinding
import br.com.alura.orgs.ui.model.Product

class ProductListAdapter(
    private val context: Context,
    productList: List<Product>
) : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    private val productListDataSet = productList.toMutableList()

    class ProductViewHolder(productItemBinding: ProductItemBinding) : RecyclerView.ViewHolder(productItemBinding.root) {
        private val nomeTv = productItemBinding.nome
        private val descricaoTv= productItemBinding.descricao
        private val valorTv = productItemBinding.valor

        fun bind(product: Product){
            nomeTv.text = product.name
            descricaoTv.text = product.description
            valorTv.text = product.value.toPlainString()
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        //val view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false)
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