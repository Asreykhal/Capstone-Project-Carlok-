package com.example.carlok.admin

import android.Manifest
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.example.carlok.R
import com.example.carlok.databinding.ActivityUploadWorkBinding
import com.example.carlok.utils.uriToFile
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.io.IOException
import java.util.*

class UploadWorkActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadWorkBinding
    private var getFile: Uri? = null
    private lateinit var auth : FirebaseAuth
    private lateinit var firestore : FirebaseFirestore
    private lateinit var storage : FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadWorkBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        firestore = Firebase.firestore
        storage = Firebase.storage

    }

    fun postUpload(view: View){
        //save pictures with different names
        val uuid= UUID.randomUUID()
        val imageName="$uuid.jpg"

        val reference=storage.reference
        val imageReference=reference.child("imagesJob").child(imageName)


        if (getFile!=null){
            imageReference.putFile(getFile!!).addOnSuccessListener {

                val uploadPictureReference=storage.reference.child("imagesJob").child(imageName)

                uploadPictureReference.downloadUrl.addOnSuccessListener {

                    val imageJob=it.toString()
                    val postMap= hashMapOf<String,Any>()
                    postMap.put("imageJob",imageJob)
                    postMap.put("userEmail",auth.currentUser!!.email!!)
                    postMap.put("namaPekerjaan",binding.etNamaPekerjaan.text.toString().trim())
                    postMap.put("tempatBekerja",binding.etTempatBekerja.text.toString().trim())
                    postMap.put("waktuBekerja",binding.etWaktuBekerja.text.toString().trim())
                    postMap.put("deksripsiPekerjaan",binding.etDeksripsiBekerja.text.toString().trim())
                    postMap.put("date", Timestamp.now())

                    firestore.collection("DaftarJobs").add(postMap).addOnSuccessListener {
                        finish()

                    }.addOnFailureListener {
                        Toast.makeText(this,it.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }.addOnFailureListener {
                Toast.makeText(this,it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun selectİmage(view: View) {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, resources.getString(R.string.pilihGambar))
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val selectedImg: Uri = it.data?.data as Uri

            val myFile = uriToFile(selectedImg, this)
            getFile = myFile.toUri()

            binding.imageJob.setImageURI(selectedImg)
        }
    }

}