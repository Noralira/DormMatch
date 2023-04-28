package com.example.dormmatch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.dormmatch.databinding.LoginActivityBinding
import com.example.dormmatch.fragments.Home

class LoginActivity : AppCompatActivity() {
    private val menuActivityRequestCode = 1
    private lateinit var binding:LoginActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)

        setContentView(binding.root)

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