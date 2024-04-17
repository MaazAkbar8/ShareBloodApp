package com.example.shareblood

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shareblood.DataModel.signin_Data
import com.example.shareblood.databinding.ActivitySignupscreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.io.IOException

class signupscreen : AppCompatActivity() {
    private lateinit var signup: TextView
    private lateinit var auth: FirebaseAuth
    private var selectedIcon: ImageView? = null

    private val PICK_IMAGE_REQUEST = 1
    private val REQUEST_IMAGE_CAPTURE = 1
    private var filePath: Uri? = null

    private lateinit var firebaseStorage1: FirebaseStorage
    private lateinit var storageReference1: StorageReference
    private var storageRef = Firebase.storage
    private lateinit var mDatabaseRef: DatabaseReference
    lateinit var binding: ActivitySignupscreenBinding


    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // intailization
        auth = Firebase.auth
        firebaseStorage1 = FirebaseStorage.getInstance()
        storageReference1 = FirebaseStorage.getInstance().reference
        auth = Firebase.auth
        storageRef = FirebaseStorage.getInstance()
        auth = FirebaseAuth.getInstance()
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("donnar_Signin")
        //************************************************************************************************************************************
        //image  work
        binding.maleicon.setOnClickListener {
            selectedIcon = binding.MaleProfile
            chooseimg()
        }

        //**********************************************************************************
        // city dropdownlist work

        val list = listOf(
            "Timergara",
            "Balambat",
            "Lal_Qilla",
            "Jandol",
            "Adenzai",
            "Samar Bagh",
            "Chakdara",
            "Munda",
            "Maidan",
            "Talash"
        )
        val adapter = ArrayAdapter(this, R.layout.list_item_city, list)
        binding.dropdownfield.setAdapter(adapter)
  //***********************************************************************************************************************

        // min width of dropdownlist
        val dynamicDimensions: Map<String, Int> = mapOf(
            "main_dropdown_width" to 600, // Set the desired value
            "other_dimension" to 150 // Set another dimension value
        )

// Access a specific dimension value
        val minWidth = dynamicDimensions["main_dropdown_width"] ?: 0

// Use the minWidth as needed
        binding.dropdownfield.dropDownWidth = minWidth


        //**********************************************************************************


        // Bloodgroup dropdownlist works

        val m = listOf("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")

        val Adapter = ArrayAdapter(this, R.layout.list_item_bloodtype, m)
        binding.dropdownfieldBloodg.setAdapter(Adapter)
   //***************************************************************************************************************

        // min width of dropdownlist
        val dynamicDimensions2: Map<String, Int> = mapOf(
            "main_dropdown_width" to 400, // Set the desired value
            "other_dimension" to 150 // Set another dimension value
        )

// Access a specific dimension value
        val minWidth2 = dynamicDimensions2["main_dropdown_width"] ?: 0

// Use the minWidth as needed
        binding.dropdownfieldBloodg.dropDownWidth = minWidth2

        //*******************************************************************************************************
    }
        //                                 in this function choosing image in gallery
        private fun chooseimg() {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE_REQUEST
            )
        }

        @SuppressLint("SuspiciousIndentation")
        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)

            if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
                if (data == null || data.data == null) {

                }
                if (data != null) {
                    filePath = data.data
                }// !!
                try {
                    val contentResolver = this.contentResolver
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                    selectedIcon?.setImageBitmap(bitmap)
//*****************************************************************************************************************************8***********
                    binding.tvsignup.setOnClickListener {

                        // save  Userimage & data realtimedatabase
                        // usename and apassword save auth
                        val name = binding.etName.text.toString()
                        val contact = binding.contact.text.toString()
                        val password = binding.password.text.toString()
                        val Re_pass = binding.rePass.text.toString()
                        val email = binding.etEmail.text.toString()
                        val city = binding.dropdownfield.text.toString()// issue place
                        val blood = binding.dropdownfieldBloodg.text.toString()

                        saveImage() // this fuction through save image storage db and Url relatimedb

                        //****************************************************************************************************
                        // edittext constrain
                        if (name.isEmpty()) {
                            Toast.makeText(this, "field is not empty", Toast.LENGTH_SHORT).show()
                        } else if (email.isEmpty()) {
                            Toast.makeText(this, " field is not empty", Toast.LENGTH_SHORT).show()
                        } else if (password.isEmpty()) {
                            Toast.makeText(this, " field is not empty", Toast.LENGTH_SHORT).show()
                        } else if (Re_pass.isEmpty()) {
                            Toast.makeText(this, " field is not emplty", Toast.LENGTH_SHORT).show()
                        } else if (city.isEmpty()) {
                            Toast.makeText(this, "field is not empty", Toast.LENGTH_SHORT).show()
                        } else if (blood.isEmpty()) {
                            Toast.makeText(this, "field is not empty", Toast.LENGTH_SHORT).show()
                            // ********************************************************************************************************
                        } else

                        // Data stored in firebase Authentication which data email and pass
                            auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {


                                        Toast.makeText(
                                            this,
                                            "signup successfully",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()

                                        val i = Intent(this, LoginScreen::class.java)
                                        startActivity(i)
                                        finish()


                                    } else {
                                        Toast.makeText(
                                            this,
                                            "signup failed${it.exception}",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                    }
                                }

                    }
                    //******************************************************************************************************
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

        }


//****************************************************************************************************************************************
        // save UserImage storage db & Url savee realtime db

        private fun saveImage( ) {

            if (filePath != null) {
                val timestamp = System.currentTimeMillis().toString()
                val imageName = "images_$timestamp"
                val imageRef =
                    storageReference1.child("DonnarImage/${imageName}.jpg")    //stored in storage  ${imageName}
                // Set a unique image name

                val uploadTask = imageRef?.putFile(filePath!!) // imageUri is the Uri of the image file

                uploadTask?.continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    imageRef.downloadUrl
                }?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        val name = binding.etName.text.toString()
                        val city = binding.dropdownfield.text.toString()
                        val blood = binding.dropdownfieldBloodg.text.toString()
                        val contact = binding.contact.text.toString()// issue place
                        val email = binding.etEmail.text.toString()
                        val password = binding.password.text.toString()
                        val Re_pass = binding.rePass.text.toString()




                        // Now you have the download URL of the uploaded image
                        Toast.makeText(this, "saved img in storage", Toast.LENGTH_SHORT)
                            .show()
                        saveImageUrlToDatabase(
                            downloadUri.toString(),
                            name.toString(),
                            city.toString(),
                            blood.toString(),
                            email.toString(),
                            contact.toString(),


                        )
                    } else {
                        // Handle failure
                    }
                }}}



        //*****************************************************************************
        // only code save image url realtime db

        private fun saveImageUrlToDatabase(images: String,name: String,city:String,blood: String, email: String, contact: String,   ) {
            val user = signin_Data(images, name, city,blood,email, contact,)// hashmap like worked right name(name),and coctact (contact)
            val uid = auth.currentUser?.uid
            if (uid != null)
                mDatabaseRef.child(uid).setValue(user).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "save data & img db", Toast.LENGTH_SHORT)
                            .show()
                        binding.etName.text?.clear()
                        binding.etEmail.text?.clear()
                        binding.dropdownfield.text?.clear()
                        binding.contact.text?.clear()
                        binding.password.text?.clear()
                        binding.rePass.text?.clear()
                        binding.dropdownfieldBloodg.text?.clear()
                    }
                }
                    .addOnFailureListener {
                        Toast.makeText(this, "not data save in db", Toast.LENGTH_SHORT)
                            .show()
                    }

    }
}













