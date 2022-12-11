package com.example.carlok.user

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.example.carlok.R
import com.example.carlok.admin.MainActivityAdmin
import com.example.carlok.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var bindingLogin: ActivityLoginBinding
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingLogin = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bindingLogin.root)
        bindingLogin.etEmailLogin.type="email"
        bindingLogin.etPasswordLogin.type="password"

        fullscreen()
        auth = FirebaseAuth.getInstance()
        bindingLogin.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        bindingLogin.btnLogin.setOnClickListener {
            val email = bindingLogin.etEmailLogin.text.toString()
            val password = bindingLogin.etPasswordLogin.text.toString()
            when {
                email.isEmpty() -> {
                    bindingLogin.etEmailLogin.error = getString(R.string.name_warning)
                }
                password.isEmpty() -> {
                    bindingLogin.etPasswordLogin.error = getString(R.string.name_warning)
                }
                else ->{
                    LoginFirebase(email,password)
                }
            }
        }

    }

    override fun onStart() {
        super.onStart()
        if(auth.currentUser !==null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    @Suppress("DEPRECATION")
    private fun fullscreen(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun LoginFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Selamat datang $email", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}