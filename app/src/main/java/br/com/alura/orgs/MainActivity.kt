package br.com.alura.orgs

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nome: TextView = findViewById<TextView>(R.id.nome)
        nome.text = "Salada de frutas"

        val descricao: TextView = findViewById<TextView>(R.id.descricao)
        descricao.text = "Banana, maçã, laranja"

        val valor: TextView = findViewById<TextView>(R.id.valor)
        valor.text = "19.99"

    }
}