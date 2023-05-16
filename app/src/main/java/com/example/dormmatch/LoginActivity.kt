package com.example.dormmatch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.dormmatch.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseApp


class LoginActivity : AppCompatActivity() {
    private val menuActivityRequestCode = 1
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        FirebaseApp.initializeApp(this)

        //setContentView(R.layout.login_activity)
    }
    fun btnLogin(view: View) {
        //setContentView(R.layout.activity_main)
        startActivityForResult(Intent(this, MenuActivity::class.java), menuActivityRequestCode)
    }
    fun btnRegister(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

}

