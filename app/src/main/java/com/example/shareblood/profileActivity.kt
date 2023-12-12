package com.example.shareblood


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.shareblood.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class profileActivity : AppCompatActivity()  {

    private lateinit var binding: ActivityProfileBinding

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
//****Start**************************************************************************************************
        // these code through  if user checked active and notactive   to perform worked










//**********************************************************************************************************







//********************************************************************************************************************
// Retrieve  Userimage URL & data  from the Realtime Database

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val databaseReference =
            userId?.let {
                FirebaseDatabase.getInstance().reference.child("donnar_Signin").child(it)
            }
        databaseReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Populate the currentUser with user data



                    val imageUrl = dataSnapshot.child("images").value.toString()
                    val name = dataSnapshot.child("name").value.toString()
                    val email = dataSnapshot.child("email").value.toString()
                    val contact = dataSnapshot.child("contact").value.toString()
                    val city = dataSnapshot.child("city").value.toString()
//

                    binding.tvName.text = name
                    binding.tvEmail.text = email
                    binding.tvContact.text = contact
                    binding.tvCity.text = city


                    // Load and display the image using Glide
                    Glide.with(this@profileActivity)
                        .load(imageUrl)
                        .into(binding.profileImage)

//                            putExtra through   go to data mainactivity
                    val sharedPreferences =
                        getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("userImageUrl", imageUrl)
                    editor.apply()// end


                }}

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
            }
        })

//***********************************************************************************************************************************
            // if user click the login btn

            binding.logoutButton.setOnClickListener {

                logout()

        }
//**********************************************************************************************************************************
                          // Activie/NotActive btn handling

        binding.ActiveNotBtn.setOnClickListener {
            val intent=Intent(this,SelectionActivity::class.java)
           // intent.putExtra("SHOW_RADIO_GROUP", true)
            startActivity(intent)
            finish()

        }


    }

//**********************************************************************************************************************************




    //*********************************************************************************************************************************
        @SuppressLint("SuspiciousIndentation")
        private fun logout() {
            FirebaseAuth.getInstance().signOut()
            // Clear the "isRegistered" flag from SharedPreferences
            val sharedPrefs = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val editor = sharedPrefs.edit()
            editor.putBoolean("isRegistered", false)
            editor.apply()


          val i =Intent(this,LoginScreen::class.java)
            startActivity(i)

        }
 //*************************************************************************************************************************************



}



