package com.example.dormmatch

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.dormmatch.databinding.CriarEditarAnuncioBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class CreateRoomActivity : AppCompatActivity() {
    private val menuActivityRequestCode = 1
    private lateinit var binding: CriarEditarAnuncioBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = CriarEditarAnuncioBinding.inflate(layoutInflater)

        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        FirebaseApp.initializeApp(this)

    }

    fun btnCriar(view: View) {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }

    fun btnVoltar(view: View) {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    fun usarCamara(view: View) {

    }

    fun selecionarFicheiros(view: View) {
    }

}