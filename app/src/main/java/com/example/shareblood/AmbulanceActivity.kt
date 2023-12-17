package com.example.shareblood

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.VISIBLE
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.shareblood.Adapters.AddEmbulnceAdapter
import com.example.shareblood.Adapters.SharedPreferencesHelper
import com.example.shareblood.DataModel.AddEmmbulncDataModel
import com.example.shareblood.databinding.ActivityAmbulanceBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AmbulanceActivity : AppCompatActivity() {

    lateinit var binding: ActivityAmbulanceBinding
    private lateinit var List2: ArrayList<AddEmmbulncDataModel>
    private lateinit var adapter2: AddEmbulnceAdapter
    private var db2 = Firebase.firestore
    private lateinit var database: DatabaseReference
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private var isNotActiveSelected :Boolean=false
    private lateinit var currentUser: AddEmmbulncDataModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAmbulanceBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.AddDonar2  .setOnClickListener {
            val i = Intent(this,makeAmbulanceMainActivity2::class.java)
            startActivity(i)
        }

        //intialization

        db2 = FirebaseFirestore.getInstance()
        binding.Rcv.layoutManager = LinearLayoutManager(this)
        database = FirebaseDatabase.getInstance().reference.child("userslist2")
      binding.Rcv.setHasFixedSize(true)
        List2 = ArrayList()
        val currentUserId: String = getCurrentUserId()
        adapter2 = AddEmbulnceAdapter(this,List2,isNotActiveSelected,currentUserId)
        binding.Rcv .adapter = adapter2

        currentUser = AddEmmbulncDataModel()


        if (List2.isNotEmpty()) {
            currentUser = List2[0]
        }
//********************************************************************************************************************************

        //************************************************************************************************************************
        //  Active and notActive radiobutton  and its works
        sharedPreferencesHelper = SharedPreferencesHelper(this)
        // Set the checked state based on the saved option
        when (sharedPreferencesHelper.getSelectedOption()) {
            "Active" -> binding.Radiogroup2.check(R.id.Active2)
            "NotActive" -> binding.Radiogroup2.check(R.id.NotActive2)

        }

        binding.Radiogroup2.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.Active2 -> {

                    sharedPreferencesHelper.saveSelectedOption("Active")
                    binding.Radiogroup2.visibility= View.GONE
                    updateAdapter()
                }

                R.id.NotActive2 -> {

                    sharedPreferencesHelper.saveSelectedOption("NotActive")
                    binding.Radiogroup2.visibility= View.GONE
                    updateAdapter()
                }
            }
        }
        updateAdapter()//     this calling through if im closed and reopen the app to selected option deside my cardview
        //   profileActivity cliked the active and notActive button work
        // Check if the flag is present in the intent
        if (intent.getBooleanExtra("SHOW_RADIO_GROUP", false)) {
            // Set the visibility of the RadioGroup to visible
            binding.Radiogroup2 .visibility = VISIBLE
        }else{
            binding.Radiogroup2 .visibility = View.GONE
        }

//*********************************************************************************************************************************
        // Get data from database in display Recyclerview

        db2.collection("Ambulancess").get().addOnSuccessListener {querySnapshot ->
            if (!querySnapshot.isEmpty) {
                for (data in querySnapshot.documents) {
                    val ambulance: AddEmmbulncDataModel? =
                        data.toObject(AddEmmbulncDataModel::class.java)
                    if (ambulance != null) {
                        List2.add(ambulance)
                    }

                    // thers adapter class
                   // binding.Rcv.adapter = AddEmbulnceAdapter(this,List2,isNotActiveSelected,)
                }
                adapter2.notifyDataSetChanged()
            }
        }
            .addOnFailureListener {
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        }




//******************************************************************************************************************************************
        // search edittext if a user search value in edittext to display all related data in recyclerview
                     // city search work
        binding.citysearch .addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val cityQuery  = s.toString()
                val hospitalQuery = binding.hospitalSearch .text.toString()
                filterItems(/*s.toString()*/hospitalQuery,cityQuery)

            }
        })
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
        binding.citysearch.setAdapter(adapter)
//******************************************************************************
        // min width of dropdownlist
        val dynamicDimensions: Map<String, Int> = mapOf(
            "main_dropdown_width" to 700, // Set the desired value
            "other_dimension" to 150 // Set another dimension value
        )

// Access a specific dimension value
        val minWidth = dynamicDimensions["main_dropdown_width"] ?: 0

// Use the minWidth as needed
        binding.citysearch.dropDownWidth = minWidth

//*******************************************************************************************************************************************
        // hospital sesarch work
        binding.hospitalSearch .addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val hospitalQuery  = s.toString()
                val cityQuery = binding.citysearch .text.toString()
                filterItems(/*s.toString()*/hospitalQuery,cityQuery)

            }
        })

        val list2= listOf(
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
        binding.hospitalSearch .setAdapter(adapter2)
//********************************************************************************************************
        // min width of dropdownlist
        val dynamicDimensions2: Map<String, Int> = mapOf(
            "main_dropdown_width" to 700, // Set the desired value
            "other_dimension" to 150 // Set another dimension value
        )

// Access a specific dimension value
        val minWidth2 = dynamicDimensions2["main_dropdown_width"] ?: 0

// Use the minWidth as needed
        binding.hospitalSearch.dropDownWidth = minWidth2

        // Fetch data from Firebase and update RecyclerView
        fetchItemsFromFirebase()

        //**********************************************************************************************************

                              // search_icon clicklistener

                     binding.searchIcon.setOnClickListener{
                         binding.lvsearch .visibility=VISIBLE
                     }
        //************************************************************************************************************
                                 //  clicked the profile icon  eventhandling
                   // Retrieve the user image URL from SharedPreferences
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userImageUrl = sharedPreferences.getString("userImageUrl", null)

        // Load and display the image using Glide
        if (!userImageUrl.isNullOrBlank()) {
            Glide.with(this)
                .load(userImageUrl)
                .circleCrop()
                .into(binding.profileImage1) // Assuming profileImage1 is the ImageView in MakeAmbulanceActivity
        }


                 binding.profileImage1.setOnClickListener {
                     val s=Intent(this,profileActivity::class.java)
                     startActivity(s)
                 }
    }
//*****************************************************************************************************************************
            // Active not active  works function
    private fun updateAdapter() {
        val isNotActiveSelected = sharedPreferencesHelper.getSelectedOption() == "NotActive"
        adapter2.updateVisibility(isNotActiveSelected)
    }
//********************************************************************************************************************************
                //if current user id controled this functon
    private fun getCurrentUserId(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        return currentUser?.uid ?: ""
    }
    // *************************************************************************************************************************
    private fun fetchItemsFromFirebase() {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    List2.clear()
                    for (itemSnapshot in snapshot.children) {
                        val item = itemSnapshot.getValue(AddEmmbulncDataModel::class.java)
                        item?.let {
                            // list.add datamodelDonarlist
                            //List2.addAll( listOf(AddEmmbulncDataModel()))
                            List2.add(AddEmmbulncDataModel())
                        }
                        adapter2.notifyDataSetChanged()
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database read error
            }
        })
    }
    //*************************************************************************************************************************************

    // this work is suposee user put name or city or bloodgroup or age is display these related all data in recyclerview
    private fun filterItems(queryHospital: String, queryCity: String) {
        val filteredItems = List2.filter {
            val isMatchinghospital = it.hospital!!.contains(queryHospital, ignoreCase = true)
            val isMatchingCity = it.city!!.equals(queryCity, ignoreCase = true)


            // Include only items that match the search query and don't match the current user when "Not Active" is selected
            isMatchingCity && isMatchinghospital

        }


        val currentUserId: String =  getCurrentUserId()
        //          if user cheked option NotActive  then search this current user  cardview = hide
        val isNotActiveSelected = sharedPreferencesHelper.getSelectedOption() == "NotActive"
        // var isNotActiveSelected = false // Default state Active and notACTVIE CHEKED

        // Filter out the current user's card view if "Not Active" is selected
        val filteredList = if (isNotActiveSelected) {
            filteredItems.filterNot { it.userId1 == currentUserId }
        } else {
            filteredItems
        }

        if (filteredList.isEmpty()) {
            // Display an empty RecyclerView
            adapter2 = AddEmbulnceAdapter(this, ArrayList(), isNotActiveSelected, currentUserId)
        } else {
            adapter2 = AddEmbulnceAdapter(this, filteredList, isNotActiveSelected, currentUserId)
        }

        binding.Rcv.adapter = adapter2

//**************************************************************************************************************************************

    }





    }
