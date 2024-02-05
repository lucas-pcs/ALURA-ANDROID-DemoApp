package br.com.alura.orgs.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.com.alura.orgs.R
import br.com.alura.orgs.ui.model.Product

class ProductListAdapter(
    private val context: Context,
    productList: List<Product>
) : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    private val productListDataSet = productList.toMutableList()

    class ProductViewHolder(view: View) : ViewHolder(view) {
        fun bind(product: Product){
            var nomeTv = itemView.findViewById<TextView>(R.id.nome)
            nomeTv.text = product.name

            var descricaoTv= itemView.findViewById<TextView>(R.id.descricao)
            descricaoTv.text = product.description

            var valorTv = itemView.findViewById<TextView>(R.id.valor)
            valorTv.text = product.value.toPlainString()
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(view)
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