package com.example.shareblood

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.shareblood.databinding.ActivityMakeAmbulanceMain2Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class makeAmbulanceMainActivity2 : AppCompatActivity() {

    private lateinit var binding:ActivityMakeAmbulanceMain2Binding
    private var db= Firebase.firestore
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMakeAmbulanceMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.tvRegister.setOnClickListener {

            val number= binding.etNumber.text.toString()
            val city=binding.etCity.text.toString()
            val hospital=binding.etHospital.text.toString()
            val  regNo=binding.etRegisteration.text.toString()


            // these code is  edittext is empty to show error message
            if (number.isEmpty()) {
                binding.etNumber .setError("field not be empty ")

            } else if (city.isEmpty()) {

                binding.etCity.setError("field not be empty")

            } else if (hospital.isEmpty()) {
                binding.etHospital.setError("field not be empty")

            } else if (regNo.isEmpty()) {

                binding.etRegisteration.setError("field not be empty")

            } else {

                // user data
                val map = hashMapOf(
                    "number" to number,
                    "city" to city,
                    "hospital" to hospital,
                    "regNo" to regNo
                )
              //  val unique = FirebaseAuth.getInstance().currentUser!!.uid // userId
                // collection ="user"   document =" userid db default create"   set= userdata
                //                           save data in databse


                 //   db.collection("Ambulancess").document(unique).set(usermap)
                        db.collection("Ambulancess").add(map)
                        .addOnSuccessListener {documentReference->
                            Toast.makeText(this, "successfully Saved", Toast.LENGTH_SHORT).show()
                            // this work is if user click button to these code through all edittext is empty or clear
                            binding.etNumber.text?.clear()
                            binding.etCity.text.clear()
                            binding.etHospital.text?.clear()
                            binding.etRegisteration.text?.clear()
                        }
                        .addOnFailureListener {e->
                            Toast.makeText(this, "Oh Failed $e", Toast.LENGTH_SHORT).show()
                        }

            }
        }
        //********************************************************************************************************************
        // city dropdownlist work

        val list= listOf("Timergara","Balambat" ,"Lal_Qilla" ,"Jandol" ,"Adenzai" ,"Samar Bagh", "Chakdara","Munda","Maidan","Talash")
        val adapter= ArrayAdapter(this,R.layout.list_item_city,list)
        binding.etCity.setAdapter(adapter)
    }
}