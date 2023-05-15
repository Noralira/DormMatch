package com.example.dormmatch


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.dormmatch.databinding.RegistoActivityBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: RegistoActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegistoActivityBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

    fun btnVoltar(view: View) {
        setContentView(R.layout.login_activity)
    }
}