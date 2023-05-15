package com.example.dormmatch


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.dormmatch.databinding.MenuBottomNavbarBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: MenuBottomNavbarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MenuBottomNavbarBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun btnReturn(view: View) {
        //setContentView(R.layout.login_activity)
    }

    fun btnCreateAccount(view: View) {
        //setContentView(R.layout.login_activity)

        // notificar se conta for criada com sucesso
    }
}