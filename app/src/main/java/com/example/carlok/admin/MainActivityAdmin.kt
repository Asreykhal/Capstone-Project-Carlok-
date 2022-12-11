package com.example.carlok.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.carlok.HomeActivity
import com.example.carlok.R
import com.example.carlok.databinding.ActivityMainAdminBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivityAdmin : AppCompatActivity() {
    private lateinit var binding : ActivityMainAdminBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        binding.fabAddJob.setOnClickListener{
            val intent = Intent(this, UploadWorkActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signOut() {
        auth.signOut()
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.lgout ->{
                signOut()
                return true
            }
        }
        return false
    }
}