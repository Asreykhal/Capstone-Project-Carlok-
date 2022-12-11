package com.example.carlok.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carlok.HomeActivity
import com.example.carlok.R
import com.example.carlok.admin.JobList
import com.example.carlok.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db : FirebaseFirestore
    private lateinit var jobsArrayList : ArrayList<JobList>
    private lateinit var jobsAdapter : ListJobAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        auth = Firebase.auth
        db = Firebase.firestore

        jobsArrayList = ArrayList<JobList>()
        getData()
        binding.rvJob.layoutManager = LinearLayoutManager(this)
        jobsAdapter = ListJobAdapter(jobsArrayList)
        binding.rvJob.adapter = jobsAdapter
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

    private fun getData(){
        db.collection("DaftarJobs").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if(error != null){
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_LONG).show()
            }else{
                if(value != null){
                    if(!value.isEmpty){
                        val documents = value.documents

                        jobsArrayList.clear()
                        for(document in documents){
                            //casting
                            val imageJob = document.get("imageJob") as String
                            val namaPekerjaan = document.get("namaPekerjaan") as String
                            val tempatBekerja = document.get("tempatBekerja") as String
                            val waktuBekerja = document.get("waktuBekerja") as String
                            val getjob = JobList(imageJob,namaPekerjaan ,tempatBekerja, waktuBekerja)
                            jobsArrayList.add(getjob)
                        }
                        jobsAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }
}