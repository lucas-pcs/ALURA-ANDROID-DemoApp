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
    private val productList: List<Product>
) : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    class ProductViewHolder(view: View) : ViewHolder(view) {
        fun bind(product: Product){
            var nomeTv = itemView.findViewById<TextView>(R.id.nome)
            nomeTv.text = product.nome

            var descricaoTv= itemView.findViewById<TextView>(R.id.descricao)
            descricaoTv.text = product.descricao

            var valorTv = itemView.findViewById<TextView>(R.id.valor)
            valorTv.text = product.valor.toPlainString()
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int {
        return productList.size
    }

}