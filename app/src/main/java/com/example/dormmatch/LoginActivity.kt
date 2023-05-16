package com.example.dormmatch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.dormmatch.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseApp
import com.example.dormmatch.databinding.MenuBottomNavbarBinding
import com.google.firebase.database.FirebaseDatabase


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
        checkLogin()
        startActivityForResult(Intent(this, MenuActivity::class.java), menuActivityRequestCode)
    }
    fun btnRegister(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun checkLogin(view: View) {
        val username = findViewById<EditText>(R.id.et_username).text.toString();
        val password = findViewById<EditText>(R.id.et_password).text.toString();

        getUsersDB()
    }

    fun getUsersDB() {
        val ref = FirebaseDatabase.getInstance().getReference("user")
    }
}