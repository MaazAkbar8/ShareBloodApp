package com.example.shareblood

import android.annotation.SuppressLint
import android.content.Intent
import android.content.Context
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout.Alignment
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.shareblood.databinding.ActivityLoginScreenBinding
import com.example.shareblood.databinding.ActivityMainBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.rpc.context.AttributeContext.Auth

class LoginScreen : AppCompatActivity() {

    //private lateinit var tvlogin:TextView
    //private lateinit var tvsignup:TextView

   lateinit var binding: ActivityLoginScreenBinding

    private lateinit var auth: FirebaseAuth



    //private lateinit var forgotpassword:TextView
  
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding= ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

         auth=FirebaseAuth.getInstance()

            // tvlogin=findViewById(R.id.tvlogin)
       // tvsignup=findViewById(R.id.tvsignup)
       // val forgotpass=findViewById<TextView>(R.id.forgotpass)



          // onclick of login
          binding.tvlogin.setOnClickListener {
              val email = binding.etEmail.text.toString()
              val password = binding.password.text.toString()

              if (email.isNotEmpty() && password.isNotEmpty()) {
                  auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                      if (it.isSuccessful) {
                          val sharedPrefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                          val editor = sharedPrefs.edit()
                          editor.putBoolean("isRegistered", true) // Replace with your actual data
                          editor.apply()
                          Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show()
                          val i = Intent(this, MainActivity::class.java)
                          startActivity(i)
                          finish()

                      } else {
                          Toast.makeText(this, " Oh!sorry: please correct your Email or pass", Toast.LENGTH_SHORT).show()
                      }

                  }


              } else {
                  Toast.makeText(this, "fields cannot be empty", Toast.LENGTH_SHORT).show()

              }

          }




        // onclick signup
             binding. tvsignup.setOnClickListener {

                 val i = Intent(this, signupscreen::class.java)
                 startActivity(i)

             }
  //    paaword_forgot eventhandling
        binding.forgotpass.setOnClickListener {
           val builder=AlertDialog.Builder(this)
            val view=layoutInflater.inflate(R.layout.pass_forgot,null)
            val userEmail=view.findViewById<EditText>(R.id.ed_Box)

            builder.setView(view)
            val dailog= builder.create()
            view.findViewById<Button>(R.id.btnReset).setOnClickListener{
            //  function for send passwordReset email
            compareEmail(userEmail)
            dailog.dismiss()


            }

            view.findViewById<Button>(R.id.btncancel).setOnClickListener {
                dailog.dismiss()
            }
            if (dailog.window != null){
                dailog.window!!.setBackgroundDrawable(ColorDrawable (0))
            }
            dailog.show( )
        }

    }


    //ourside oncreate

     private fun compareEmail(email:EditText){

         if(email.text.toString().isEmpty()){
             return
         }
         if(!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()){
             
            return
         }
         auth.sendPasswordResetEmail(email.text.toString()).addOnCompleteListener {Task->

          if (Task.isSuccessful){
              Toast.makeText(this, "please check your Email", Toast.LENGTH_SHORT).show()
          }

         }
     }
}