package com.example.appnotciasadm

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appnotciasadm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        binding.btnPublicar.setOnClickListener {
            var título = binding.etTitulo
            var data = binding.etData
            var notícia = binding.etNoticia
            var autor = binding.etAutor

            if (título.text.toString().isEmpty() || data.toString().isEmpty() ||
                notícia.toString().isEmpty() || autor.toString().isEmpty()) Toast
                .makeText(this, R.string.campo_vazio, Toast.LENGTH_SHORT).show()
            else {
                título.text.clear()
                notícia.text.clear()
                data.text.clear()
                autor.text.clear()
                Toast.makeText(this, R.string.publicado, Toast.LENGTH_SHORT).show()
            }
        }
    }
}