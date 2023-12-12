package com.example.shareblood

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
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


        //**********************************************************************************


        // Bloodgroup dropdownlist works

        val m = listOf(" (A+)", " (A-)", "(B+) ", "(B-) ", " (AB+)", "(AB-) ", " (O+) ", "(O-)")

        val Adapter = ArrayAdapter(this, R.layout.list_item_bloodtype, m)
        binding.dropdownfieldBloodg.setAdapter(Adapter)

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











































        // signup eventhandling
        /* binding.tvsignup.setOnClickListener {
            val name = binding.etName.getText().toString()
            val dropdownfield = binding.dropdownfield.getText().toString()
            val contact = binding.contact.getText().toString()
            val blood = binding.dropdownfieldBloodg.getText().toString()
            val Email = binding.etEmail.getText().toString()
            val pass = binding.password.getText().toString()
            val re_pass = binding.rePass.getText().toString()

            saveimage()

            // saveImage() // this fuction through save image storage db and Url relatimedb

            if (contact.isEmpty()) {
                binding.contact.setError("fields not be empty")
            } else if (blood.isEmpty()) {
                binding.dropdownfieldBloodg.setError("fields not be empty")
            } else if (Email.isEmpty()) {
                binding.etEmail.setError("filed not be empty")
            } else if (pass.isEmpty()) {
                binding.password.setError("field not be empty")
            } else if (re_pass.isEmpty()) {
                binding.rePass.setError("field not be empty")

            } else {
                // Data stored in firebase Authentication which data email and pass
                auth.createUserWithEmailAndPassword(Email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "signup successfully", Toast.LENGTH_SHORT)
                            .show()
                        val i = Intent(this, LoginScreen::class.java)
                        startActivity(i)
                    } else {
                        Toast.makeText(
                            this,
                            "signup failed${it.exception}",
                            Toast.LENGTH_SHORT
                        )
                            .show()

                    }

                }
                //******************************************************************************************************
            }

        }
    }

    private fun saveimage() {
        if (filePath != null) {
            val timestamp = System.currentTimeMillis().toString()
            val imageName = "imge_$timestamp"
            val imageRef =
                storageReference1?.child("Donnar/${imageName}.jpg")    //stored in storage  ${imageName}
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
                    val dropdownfield = binding.dropdownfield.text.toString()
                    val blood = binding.dropdownfieldBloodg.text.toString()
                    val email = binding.etEmail.text.toString()
                    val contact = binding.contact.getText().toString()



                    // Now you have the download URL of the uploaded image
                    Toast.makeText(this, "saved img in storage", Toast.LENGTH_SHORT)
                        .show()
                    saveImageUrlToDatabase(
                        downloadUri.toString(),
                        name.toString(),
                        dropdownfield.toString(),
                        blood.toString(),
                        email.toString(),
                        contact.toString()

                    )
                } else {
                    // Handle failure
                }
            }
        }


    }
    private fun saveImageUrlToDatabase(
        images: String,
        name: String,
        dropdownfield: String,
        contact: Any,
        blood:String,
        email: String,


        ) {
        val user = signin_Data(images, name,dropdownfield, email, contact,blood)
        val uid = auth.currentUser?.uid
        if (uid != null)
            mDatabaseRef.child(uid).setValue(user).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "save data & img db", Toast.LENGTH_SHORT)
                        .show()
                    binding.etName.text?.clear()
                    binding.etEmail.text?.clear()
                    binding.dropdownfield.text?.clear()
                    binding.dropdownfieldBloodg
                    binding.contact.text?.clear()
                    binding.password.text?.toString()
                    binding.rePass.text?.toString()
                }
            }
                .addOnFailureListener {
                    Toast.makeText(this, "not data save in db", Toast.LENGTH_SHORT)
                        .show()
                }


        /* val databaseRef = FirebaseDatabase.getInstance().reference
         val newImageKey =
             databaseRef.child("photo").push().key // Generate a new key user childroot

         val imageItem =
             signinData(img, name, email, gender, proffession) // dataclass data put here
         newImageKey?.let {
             databaseRef.child("userData").child(it)
                 .setValue(imageItem)// image2 save realtime datbase rootname
                 .addOnSuccessListener {
                     // Image URL stored in the database successfully
                     Toast.makeText(requireContext(), "saved imageurl DB", Toast.LENGTH_SHORT)
                         .show()
//                          constraint
                     binding.Name.text?.clear()
                     binding.etEmail.text?.clear()
                     binding.dropdownfieldGender.text?.clear()
                     binding.profession.text?.clear()
                     binding.password.text?.toString()
                     binding.RePassword.text?.toString()
                 }
                 .addOnFailureListener {
                     // Handle failure
                     Toast.makeText(requireContext(), "not saved", Toast.LENGTH_SHORT).show()
                 }
         }*/


    }
//*********************************************** end of functionality ********************************************************



private fun chooseimg() {

        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
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

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }*/



 */






}*/



        */
    }
}








            //*****************************************************************************************************************





