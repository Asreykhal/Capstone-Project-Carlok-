package com.example.carlok.user

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.example.carlok.R
import com.example.carlok.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var bindingRegis : ActivityRegisterBinding
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingRegis = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(bindingRegis.root)
        bindingRegis.etEmailRegister.type="email"
        bindingRegis.etPasswordRegister.type="password"
        fullscreen()
        auth = FirebaseAuth.getInstance()
        bindingRegis.btnRegister.setOnClickListener {
            val email = bindingRegis.etEmailRegister.text.toString()
            val password = bindingRegis.etPasswordRegister.text.toString()
            when {
                email.isEmpty() -> {
                    bindingRegis.etEmailRegister.error = getString(R.string.name_warning)
                }
                password.isEmpty() -> {
                    bindingRegis.etPasswordRegister.error = getString(R.string.name_warning)
                }
                else ->{
                    RegisterFirebase(email,password)
                }
            }
        }
    }

    private fun RegisterFirebase(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Register Berhasil", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
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
}