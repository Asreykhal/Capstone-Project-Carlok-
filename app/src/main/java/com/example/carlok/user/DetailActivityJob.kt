package com.example.carlok.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.carlok.R
import com.example.carlok.databinding.ActivityDetailJobBinding

class DetailActivityJob : AppCompatActivity() {

    private lateinit var binding: ActivityDetailJobBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_job)
        binding =ActivityDetailJobBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intss = intent
        var nameT = intss.getStringExtra("namaPekerjaan")

        binding.nameDetailTextView.text = nameT
    }


}