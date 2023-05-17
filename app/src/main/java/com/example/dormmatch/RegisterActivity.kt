package com.example.dormmatch


import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.dormmatch.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload()
        }
    }

    private fun createAccount(email: String, password: String, username: String) {
        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser

                    val userProfileChangeRequest = UserProfileChangeRequest.Builder()
                        .setDisplayName(username)
                        .build()

                    user?.updateProfile(userProfileChangeRequest)
                        ?.addOnCompleteListener { updateTask ->
                            if (updateTask.isSuccessful) {
                                showPopup(this, "Information", "Account successfully created.", user)

                            } else {
                                // Failed to update additional user information
                            }
                        }

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(null)
                }
            }
        // [END create_user_with_email]
    }

    private fun updateUI(user: FirebaseUser?) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun reload() {
    }

    companion object {
        private const val TAG = "EmailPassword"
    }

    fun btnReturn(view: View) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    fun btnCreateAccount(view: View) {

        val email = findViewById<EditText>(R.id.et_email).text.toString()
        val password = findViewById<EditText>(R.id.et_password).text.toString().trim()
        val username = findViewById<EditText>(R.id.et_username).text.toString()

        var emailIsCorrect: Boolean = checkEmail(email)
        var passwordIsCorrect:Boolean = checkPassword(password)
        var usernameIsCorrect:Boolean = checkUsername(username)

        if(emailIsCorrect && passwordIsCorrect && usernameIsCorrect) {
            createAccount(email, password, username)
        }
    }

    private fun checkUsername(username: String):Boolean {

        if (username.isEmpty()) {
            findViewById<EditText>(R.id.et_username).error = "Username is required"
            return false
        }

        return true
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

    fun showPopup(context: Context, title: String, message: String, user: FirebaseUser?) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss() // Close the popup when OK button is clicked
            updateUI(user)
        }
        val dialog = builder.create()
        dialog.show()
    }
}