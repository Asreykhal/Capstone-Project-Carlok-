package com.example.carlok.admin

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.example.carlok.R
import com.example.carlok.databinding.ActivityRegisterAdminBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivityAdmin : AppCompatActivity() {
    private lateinit var bindingRegis : ActivityRegisterAdminBinding
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingRegis = ActivityRegisterAdminBinding.inflate(layoutInflater)
        setContentView(bindingRegis.root)
        bindingRegis.etEmailRegisterAdmin.type="email"
        bindingRegis.etPasswordRegisterAdmin.type="password"
        fullscreen()
        auth = FirebaseAuth.getInstance()
        bindingRegis.btnRegisterAdmin.setOnClickListener {
            val email = bindingRegis.etEmailRegisterAdmin.text.toString()
            val password = bindingRegis.etPasswordRegisterAdmin.text.toString()
            when {
                email.isEmpty() -> {
                    bindingRegis.etEmailRegisterAdmin.error = getString(R.string.name_warning)
                }
                password.isEmpty() -> {
                    bindingRegis.etPasswordRegisterAdmin.error = getString(R.string.name_warning)
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
                    val intent = Intent(this, AdminLogin::class.java)
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