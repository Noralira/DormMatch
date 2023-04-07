package com.example.dormmatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
    }

    fun btnLogin(view: View) {   setContentView(R.layout.home_user)}
    fun btnRegister(view: View) {
        setContentView(R.layout.registo_activity)
    }

    fun btnVoltar(view: View) {
        setContentView(R.layout.login_activity)
    }
}