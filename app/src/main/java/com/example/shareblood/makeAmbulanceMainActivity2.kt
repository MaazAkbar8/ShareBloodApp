package com.example.shareblood

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
        binding = ActivityMakeAmbulanceMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.tvRegister.setOnClickListener {

            val number = binding.etNumber.text.toString()
            val city = binding.etCity.text.toString()
            val hospital = binding.etHospital.text.toString()
            val RegNo = binding.etRegisteration.text.toString()


            // these code is  edittext is empty to show error message
            if (number.isEmpty()) {
                binding.etNumber.setError("field not be empty ")

            } else if (city.isEmpty()) {

                binding.etCity.setError("field not be empty")

            } else if (hospital.isEmpty()) {
                binding.etHospital.setError("field not be empty")

            } else if (RegNo.isEmpty()) {

                binding.etRegisteration.setError("field not be empty")

            } else {
                val userId1 = FirebaseAuth.getInstance().currentUser?.uid
                if (userId1 != null) {

                    // user data
                    val map = hashMapOf(
                        "userId1" to userId1,
                        "number" to number,
                        "city" to city,
                        "hospital" to hospital,
                        "RegNo" to RegNo
                    )
                    //  val unique = FirebaseAuth.getInstance().currentUser!!.uid // userId
                    // collection ="user"   document =" userid db default create"   set= userdata
                    //                           save data in databse


                    //   db.collection("Ambulancess").document(unique).set(usermap)
                    db.collection("Ambulancess").add(map)
                        .addOnSuccessListener { documentReference ->
                            Toast.makeText(this, "successfully Saved", Toast.LENGTH_SHORT).show()
                            // this work is if user click button to these code through all edittext is empty or clear
                            binding.etNumber.text?.clear()
                            binding.etCity.text.clear()
                            binding.etHospital.text?.clear()
                            binding.etRegisteration.text?.clear()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Oh Failed $e", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }
        //********************************************************************************************************************
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
        binding.etCity .setAdapter(adapter)
        //            ************************************************************************************
        // min width of dropdownlist
        val dynamicDimensions2: Map<String, Int> = mapOf(
            "main_dropdown_width" to 700, // Set the desired value
            "other_dimension" to 150 // Set another dimension value
        )

// Access a specific dimension value
        val minWidth2 = dynamicDimensions2["main_dropdown_width"] ?: 0

// Use the minWidth as needed
        binding.etCity.dropDownWidth = minWidth2
        //       ****************************************************************************************

        //************************************************************************************************************************
        // hospital dropdownlist

        val list2 = listOf(
            "Tahsel headquarter hospital",
            "DHQ hospital",
            "shifa hospital",
            " City Hospital",
            " Dr. Shafi Hospital",
            " Amin Surgical Hospital",
            " Life-Saving Children Hospital",
            "Abeer saeed hospital",
            "seena medical hospital",
            "RHC hospital"
        )
        val adapter2 = ArrayAdapter(this, R.layout.list_item_hospital, list2)
        binding.etHospital .setAdapter(adapter2)

//            ************************************************************************************
                              // min width of dropdownlist
        val dynamicDimensions: Map<String, Int> = mapOf(
            "main_dropdown_width" to 700, // Set the desired value
            "other_dimension" to 150 // Set another dimension value
        )

// Access a specific dimension value
        val minWidth = dynamicDimensions["main_dropdown_width"] ?: 0

// Use the minWidth as needed
        binding.etHospital.dropDownWidth = minWidth
    //       ****************************************************************************************
    }
}