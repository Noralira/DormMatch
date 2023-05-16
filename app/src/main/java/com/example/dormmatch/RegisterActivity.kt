package com.example.dormmatch


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.dormmatch.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

    fun btnReturn(view: View) {
        setContentView(R.layout.activity_register)
    }

    fun btnCreateAccount(view: View) {
        setContentView(R.layout.activity_register)

        // notificar se conta for criada com sucesso
    }
}