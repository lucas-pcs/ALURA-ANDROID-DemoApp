package br.com.alura.orgs.dao

import br.com.alura.orgs.ui.model.Product

class ProductDAO {

    fun addProduct(product: Product){
        productList.add(product)
    }

    fun getProductList(): List<Product>{
        return productList.toList()
    }

    companion object {
        private val productList = mutableListOf<Product>()
    }
}