package com.example.dormmatch

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.dormmatch.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.ktx.Firebase



class LoginActivity : AppCompatActivity() {
    private val menuActivityRequestCode = 1
    private lateinit var binding: ActivityLoginBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        FirebaseApp.initializeApp(this)

    }

    private fun signIn(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        val exception = task.exception as FirebaseAuthInvalidCredentialsException
                        val errorCode = exception.errorCode

                        if (errorCode == "ERROR_WRONG_PASSWORD") {
                            // Wrong password
                            Toast.makeText(this, "Wrong password", Toast.LENGTH_SHORT).show()
                        }
                        if(errorCode == "ERROR_USER_NOT_FOUND") {
                            Toast.makeText(this, "Wrong Email", Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        // Other sign-in errors
                        // Handle other sign-in errors
                    }
                }
            }
    }

    fun btnLogin(view: View) {
        val email = findViewById<EditText>(R.id.et_email).text.toString();
        val password = findViewById<EditText>(R.id.et_password).text.toString().trim();

        var emailIsCorrect: Boolean = checkEmail(email)
        var passwordIsCorrect:Boolean = checkPassword(password)

        if(emailIsCorrect && passwordIsCorrect) {
            signIn(email, password)
        }
    }
    fun btnRegister(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun updateUI(user: FirebaseUser?) {
        val intent = Intent(this, MenuActivity::class.java)
        intent.putExtra("user", user)
        startActivity(intent)
    }


    private fun checkEmail(email: String):Boolean {

        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        if (email.isEmpty()) {
            findViewById<EditText>(R.id.et_email).error = "Email is required"
            return false
        }
        else if (!email.matches(emailPattern.toRegex())) {
            findViewById<EditText>(R.id.et_email).error = "Invalid email address"
            return false
        }

        return true
    }

    private fun checkPassword(password: String):Boolean {
        val minLength = 6

        if (password.isEmpty()) {
            findViewById<EditText>(R.id.et_password).error = "Password is required"
            return false
        }
        else if (password.length < minLength) {
            findViewById<EditText>(R.id.et_password).error = "Password must be at least $minLength characters"
            return false
        }

        return true
    }
}