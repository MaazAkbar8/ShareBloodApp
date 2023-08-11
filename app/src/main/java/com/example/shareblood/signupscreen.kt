package com.example.shareblood

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.example.shareblood.databinding.ActivityLoginScreenBinding
import com.example.shareblood.databinding.ActivitySignupscreenBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.jar.Attributes.Name

class signupscreen : AppCompatActivity() {
    private lateinit var signup:TextView

    private lateinit var auth: FirebaseAuth
    lateinit var binding: ActivitySignupscreenBinding
    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignupscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
       // intailization
        auth = Firebase.auth

        //**********************************************************************************
            // Email dropdownlist work

             val list= listOf("Timergara","Balambat" ,"Lal_Qilla" ,"Jandol" ,"Adenzai" ,"Samar Bagh", "Chakdara","Munda","Maidan","Talash")
             val adapter=ArrayAdapter(this,R.layout.list_item_city,list)
             binding.dropdownfield.setAdapter(adapter)



        //**********************************************************************************


             // Bloodgroup dropdownlist works

                 val m= listOf(" (A+)", " (A-)", "(B+) ","(B-) "," (AB+)", "(AB-) "," (O+) ","(O-)")

                 val Adapter=ArrayAdapter(this,R.layout.list_item_bloodtype,m)
                   binding.dropdownfieldBloodg.setAdapter(Adapter)

  //*******************************************************************************************************












        // signup eventhandling
        binding.tvsignup.setOnClickListener {
            val name = binding.etName.getText().toString()
            val dropdownfield= binding.dropdownfield.getText().toString()
            val contact = binding.contact.getText().toString()
            val blood = binding.dropdownfieldBloodg.getText().toString()
            val Email = binding.etEmail.getText().toString()
            val pass = binding.password.getText().toString()
            val re_pass = binding.rePass.getText().toString()

            if (contact.isEmpty()) {
                binding.contact.setError("fields not be empty")
            } else if (blood.isEmpty()) {
                binding.dropdownfieldBloodg.setError("fields not be empty")
            } else if (Email.isEmpty()) {
                binding.etEmail.setError("filed not be empty")
            }else if (pass.isEmpty()) {
                binding.password.setError("field not be empty")
            }else if (re_pass.isEmpty()) {
                binding.rePass.setError("field not be empty")

            }else {
                // Data stored in firebase Authentication which data email and pass
                auth.createUserWithEmailAndPassword(Email, pass).addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(this, "signup successfully", Toast.LENGTH_SHORT).show()
                        val i = Intent(this, LoginScreen::class.java)
                        startActivity(i)
                    }else{
                        Toast.makeText(this, "signup failed${it.exception}", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
        //*****************************************************************************************************************


    }
}