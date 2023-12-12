package com.example.shareblood

import android.annotation.SuppressLint
import android.content.ClipData.Item
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shareblood.databinding.ActivityMakeDonarBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MakeDonar : AppCompatActivity() {
    private lateinit var postdonar: TextView
    private lateinit var binding:ActivityMakeDonarBinding
    private lateinit var searching:Item
    private var db= Firebase.firestore
    @SuppressLint("MissingInflatedId", "RestrictedApi", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMakeDonarBinding.inflate(layoutInflater)
        setContentView(binding.root)




     binding.tvpostdonar.setOnClickListener {
         //  val i=Intent(this,MainActivity::class.java)
         // startActivity(i)
         val name = binding.etName.text.toString()
         val city = binding.etCity.text.toString()
         val bloodGroup = binding.etBloodGroup.text.toString()
         val age = binding.etAge.text.toString()
         val mobilenumber=binding.etContact.text.toString()

//*********************************************************************************************************************************
         // these code is  edittext is empty to show error message
         if (name.isEmpty()) {
             binding.etName.setError("field not be empty ")

         } else if (city.isEmpty()) {

             binding.etCity.setError("field not be empty")

         } else if (bloodGroup.isEmpty()) {
            binding.etBloodGroup.setError("field not be empty")

         } else if (age.isEmpty()) {

             binding.etAge.setError("field not be empty")
      //*******************************************************************************************************************

         } else if (mobilenumber.isEmpty()){
             binding.etContact.setError("field not be empty")

         }else {
             // Get the current user ID
             val userId = FirebaseAuth.getInstance().currentUser?.uid
             if (userId != null) {


                 // user data
                 val usermap = hashMapOf(
                     "userId" to userId,
                     "name" to name,
                     "city" to city,
                     "bloodGroup" to bloodGroup,
                     "age" to age,
                     "mobilenumber" to mobilenumber

                 )

                 // val userId = FirebaseAuth.getInstance().currentUser!!.uid
                 // collection ="user"   document =" userid db default create"   set= userdata
                 //                  (save data in databse)

                 // db.collection("user").document(userId).set(usermap)
                 db.collection("user").add(usermap)
                     .addOnSuccessListener { documentReference ->
                         Toast.makeText(this, "successfully Saved", Toast.LENGTH_SHORT).show()
                         // this work is if user click button to these code through all edittext is empty or clear
                         binding.etName.text?.clear()
                         binding.etCity.text.clear()
                         binding.etBloodGroup.text.clear()
                         binding.etAge.text?.clear()
                         binding.etContact.text?.clear()
                     }
                     .addOnFailureListener { e ->
                         Toast.makeText(this, "Oh Failed $e", Toast.LENGTH_SHORT).show()
                     }
             }

         }
     }





   //********************************************************************************************************************
        // city dropdownlist work

        val list= listOf("Timergara","Balambat" ,"Lal_Qilla" ,"Jandol" ,"Adenzai" ,"Samar Bagh", "Chakdara","Munda","Maidan","Talash")
        val adapter= ArrayAdapter(this,R.layout.list_item_city,list)
        binding.etCity.setAdapter(adapter)

        //            ************************************************************************************
        // min width of dropdownlist
        val dynamicDimensions2: Map<String, Int> = mapOf(
            "main_dropdown_width" to 600, // Set the desired value
            "other_dimension" to 150 // Set another dimension value
        )

// Access a specific dimension value
        val minWidth2 = dynamicDimensions2["main_dropdown_width"] ?: 0

// Use the minWidth as needed
        binding.etCity.dropDownWidth = minWidth2
        //       ****************************************************************************************

        //*******************************************************************************************************************

        // Blood type works

        // Bloodgroup dropdownlist works

        val m= listOf("A+", "A-", "B+","B-","AB+", "AB-","O+","O-")

        val Adapter=ArrayAdapter(this,R.layout.list_item_bloodtype,m)
        binding.etBloodGroup.setAdapter(Adapter)
        //  ************************************************************************************
        // min width of dropdownlist
        val dynamicDimensions: Map<String, Int> = mapOf(
            "main_dropdown_width" to 400, // Set the desired value
            "other_dimension" to 150 // Set another dimension value
        )

// Access a specific dimension value
        val minWidth = dynamicDimensions["main_dropdown_width"] ?: 0

// Use the minWidth as needed
        binding.etBloodGroup.dropDownWidth = minWidth
        //       ****************************************************************************************
//*****************************************************************************************************************************


    }




}