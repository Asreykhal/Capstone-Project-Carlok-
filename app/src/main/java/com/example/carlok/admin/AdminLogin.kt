package com.example.carlok.admin

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.example.carlok.R
import com.example.carlok.databinding.ActivityAdminLoginBinding
import com.example.carlok.user.MainActivity
import com.google.firebase.auth.FirebaseAuth

class AdminLogin : AppCompatActivity() {
    private lateinit var binding: ActivityAdminLoginBinding
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.etEmailLoginAdmin.type="email"
        binding.etPasswordLoginAdmin.type="password"
        fullscreen()
        auth = FirebaseAuth.getInstance()
        binding.btnRegisterAdmin.setOnClickListener {
            val intent = Intent(this, RegisterActivityAdmin::class.java)
            startActivity(intent)
        }
        binding.btnLoginAdmin.setOnClickListener {
            val email = binding.etEmailLoginAdmin.text.toString()
            val password = binding.etPasswordLoginAdmin.text.toString()
            when {
                email.isEmpty() -> {
                    binding.etEmailLoginAdmin.error = getString(R.string.name_warning)
                }
                password.isEmpty() -> {
                    binding.etPasswordLoginAdmin.error = getString(R.string.name_warning)
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
            val intent = Intent(this, MainActivityAdmin::class.java)
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
                    val intent = Intent(this, MainActivityAdmin::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}