@file:Suppress(
    "LocalVariableName",
    "SpellCheckingInspection",
    "NonAsciiCharacters",
    "FunctionName"
)

package com.example.appnotciasadm

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.appnotciasadm.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val db = FirebaseFirestore.getInstance()

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        val hoje = SimpleDateFormat("dd/MM/yyyy")
        val data = hoje.format(Date()).toString()
        binding.etData.setText(data)

        binding.btnPublicar.setOnClickListener {

            val título = binding.etTitulo.text.toString()
            val notícia = binding.etNoticia.text.toString()
            val autor = binding.etAutor.text.toString()

            if (título.isEmpty() || data.isEmpty() || notícia.isEmpty() || autor.isEmpty())
                Toast.makeText(this, R.string.campo_vazio, Toast.LENGTH_SHORT).show()
            else {
                if (título.length < 4) Toast.makeText(
                    this,
                    R.string.título_curto,
                    Toast.LENGTH_SHORT
                ).show()
                else if (notícia.length < 25) Toast.makeText(
                    this,
                    R.string.notícia_curta,
                    Toast.LENGTH_SHORT
                ).show()
                else salvarNotícia(
                    título,
                    notícia,
                    data,
                    autor
                )
            }

            recolherTeclado()
        }
    }

    private fun salvarNotícia(título: String, notícia: String, data: String, autor: String) {
        val mapNotícias = hashMapOf(
            "título" to título,
            "notícia" to notícia,
            "data" to data,
            "autor" to autor
        )

        db.collection("notícias").document("${título.trim()}(${autor.trim()})")
            .set(mapNotícias).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, R.string.publicado, Toast.LENGTH_SHORT).show()
                    limparCampos()
                }
            }.addOnFailureListener {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle(R.string.erro)
                    .setMessage(R.string.bd_erro)
                    .setPositiveButton("OK") { _, _ -> }
                    .show()
            }
    }

    private fun limparCampos() {
        binding.etTitulo.text.clear()
        binding.etNoticia.text.clear()
        binding.etAutor.text.clear()
    }

    private fun recolherTeclado() {
        val view: View? = this.currentFocus

        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}

