package com.example.shareblood


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.shareblood.Adapters.MAkeDonarAdapter
import com.example.shareblood.Adapters.SharedPreferencesHelper
import com.example.shareblood.DataModel.DataModelDonorList
import com.example.shareblood.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase



class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var List1: ArrayList<DataModelDonorList>

    private lateinit var adapter: MAkeDonarAdapter
    // private lateinit var swipeRefresh: SwipeRefreshLayout

    private var isActive: Boolean = false
    private lateinit var currentUser: DataModelDonorList

    // data stored and retrieve need to these line
    private var db = Firebase.firestore
    private lateinit var database: DatabaseReference

    private var isNotActiveSelected :Boolean=false
    private lateinit var pref: SharedPreferences
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
   //*****************************************************************************************************************
        //   intialization

        List1 = ArrayList()
        db = FirebaseFirestore.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("usersList")
        binding.Rcv2.layoutManager = LinearLayoutManager(this)
        binding.Rcv2.setHasFixedSize(true)


        val currentUserId: String = getCurrentUserId()

        adapter = MAkeDonarAdapter(this, List1,isNotActiveSelected,currentUserId)

        binding.Rcv2.adapter = adapter


        currentUser = DataModelDonorList()


        if (List1.isNotEmpty()) {
            currentUser = List1[0]
        }




//************************************************************************************************************************
                  //  Active and notActive radiobutton  and its works
        sharedPreferencesHelper = SharedPreferencesHelper(this)
        // Set the checked state based on the saved option
        when (sharedPreferencesHelper.getSelectedOption()) {
            "Active" -> binding.Radiogroup.check(R.id.Active)
            "NotActive" -> binding.Radiogroup.check(R.id.NotActive)

        }

        binding.Radiogroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.Active -> {

                    sharedPreferencesHelper.saveSelectedOption("Active")
                    updateAdapter()
                }

                R.id.NotActive -> {

                    sharedPreferencesHelper.saveSelectedOption("NotActive")
                    updateAdapter()
                }
            }
        }
        updateAdapter()//     this caling through if im closed and reopen the app to selected option deside my cardview
//******************************************************************************************************************************************
                           //   profileActivity cliked the active and notActive button work
        // Check if the flag is present in the intent
        if (intent.getBooleanExtra("SHOW_RADIO_GROUP", false)) {
            // Set the visibility of the RadioGroup to visible
            binding.Radiogroup .visibility = VISIBLE
        }else{
            binding.Radiogroup .visibility = GONE
        }

//***************************************************************************************************************************************
        // MainActivity set profile image works
        // Retrieve user image URL from SharedPreferences
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userImageUrl = sharedPreferences.getString("userImageUrl", "")


        // Get the profile_icon menu item
        val profileIconMenuItem = binding.Toolbar.menu.findItem(R.id.profile_icon)

        // Load and display the image using Glide in the menu item icon
        if (!userImageUrl.isNullOrBlank()) {
            Glide.with(this)
                .load(userImageUrl)
                .circleCrop()
                .into(object : CustomTarget<Drawable>() {
                    override fun onLoadCleared(placeholder: Drawable?) {
                        // If needed, you can handle what happens when the image loading is cleared
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?
                    ) {
                        profileIconMenuItem.icon = resource
                    }
                })


        }

//***********************************************************************************************************************************

        // Get data from database in display Recyclerview

        db.collection("user").get().addOnSuccessListener { querySnapshot ->


           // if (!it.isEmpty) {
               // for (data in it.documents) {
            if (!querySnapshot.isEmpty) {
                for (data in querySnapshot.documents) {
                    val users: DataModelDonorList? =
                        data.toObject(DataModelDonorList::class.java)

                    if (users != null) {
                        List1.add(users)

                    }
                }
                adapter.notifyDataSetChanged()
                // thers adapter class
            }   // binding.Rcv2.adapter = MAkeDonarAdapter(this, List,isActive,currentUser)


               // }

                /*  if (List.isNotEmpty()) {
                    currentUser = List[0] // For example, choose the first user in the list
                    this.isActive = false // Set isActive based on your logic



                }*/

            //}


        }

            .addOnFailureListener {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()

            }

        fetchItemsFromFirebase()
        //**********************************************************************************************************
        // Top bar totally works
        binding.Toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                //  embulance icon work
                R.id.embulance -> {
                    this.startActivity(Intent(this, AmbulanceActivity::class.java))

                    return@setOnMenuItemClickListener false
                }

                // search icon work
                R.id.search -> {
                    binding.lvsearch.visibility = VISIBLE

                    return@setOnMenuItemClickListener true
                }
                // profile icon work
                R.id.profile_icon -> {
                    //startProfileActivity()// new work
                    this.startActivity(Intent(this, profileActivity::class.java))
                    return@setOnMenuItemClickListener false
                    // new work
                }


                else -> {
                    super.onOptionsItemSelected(it)

                }
            }

        }
//*******************************************************************************************************************
        binding.AddDonar.setOnClickListener {
            val i = Intent(this, MakeDonar::class.java)
            startActivity(i)
        }


        //***********************************************************************************************************************

        // Edittext search work
        binding.citysearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val bloodTypeQuery = binding.bloodtypesearch.text.toString()
                val cityQuery = s.toString()
                filterItems(/*s.toString()*/ bloodTypeQuery,cityQuery)

            }
        })

        // Email dropdownlist work

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

        //*************************************************************************************************************************

        // Edittext search work
        binding.bloodtypesearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val bloodTypeQuery = s.toString()
                val cityQuery = binding.citysearch.text.toString()
                filterItems(/*s.toString()*/bloodTypeQuery,cityQuery)

            }
        })


        // Bloodgroup dropdownlist works

        val m = listOf("(A+)", "(A-)", "(B+)", "(B-)", "(AB+)", "AB-", " (O+)", "O-")

        val Adapter = ArrayAdapter(this, R.layout.list_item_bloodtype, m)
        binding.bloodtypesearch.setAdapter(Adapter)



//****************************************************************************************************************
    }
                 //Active and notActive radio button   fuction
    private fun updateAdapter() {
        val isNotActiveSelected = sharedPreferencesHelper.getSelectedOption() == "NotActive"
        adapter.updateVisibility(isNotActiveSelected)
    }

//**************************************************************************************************************************************
       // back btn cliked destroy app
    override fun onBackPressed() {
        // Optionally add any cleanup or save state logic here before finishing the activity
        // ...

        finishAffinity() // This will finish the current activity and all parent activities
    }
//*****************************************************************************************************************************************
    private fun getCurrentUserId(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        return currentUser?.uid ?: ""
    }
// *************************************************************************************************************************


    private fun fetchItemsFromFirebase() {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    List1.clear()// List.

                    for (itemSnapshot in snapshot.children) {
                       // val item = itemSnapshot.getValue(ClipData.Item::class.java)
                        val item = itemSnapshot.getValue(DataModelDonorList::class.java)

                        item?.let {
                            // list.add datamodelDonarlist
                            List1.add(DataModelDonorList())
                        }




                        adapter.notifyDataSetChanged()

                    }
                    // new


                }

            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database read error
                // .isRefreshing = false


            }
        })


    }

    // *****************************************************************************************************************
              // search works
    // this work is suposee user put name or city or bloodgroup or age is display these related all data in recyclerview
    private fun filterItems(/*query: String*/ queryBloodType: String, queryCity: String) {
        val filteredItems = List1.filter {
            // it.name!!.contains(query, ignoreCase = true) ||
            //   it.age!!.contains(query, ignoreCase = true) ||
            //(it.city!!.contains(query, ignoreCase = true) ||
                   // it.bloodGroup!!.contains(query, ignoreCase = true))
            //val isCurrentUserId = it.userId == getCurrentUserId()
            val isMatchingBloodGroup = it.bloodGroup!!.contains(queryBloodType, ignoreCase = true)
            val isMatchingCity = it.city!!.equals(queryCity, ignoreCase = true)



            // Include only items that match the search query and don't match the current user when "Not Active" is selected
            isMatchingCity && isMatchingBloodGroup
        }


        val currentUserId: String =  getCurrentUserId()
        var isNotActiveSelected = false // Default state Active and notACTVIE CHEKED
        //adapter = MAkeDonarAdapter(this, filteredItems,isNotActiveSelected,currentUserId)// da  o pke da this an bad =filteredItems

    if (filteredItems.isEmpty()) {
        // Display an empty RecyclerView
        adapter = MAkeDonarAdapter(this, ArrayList(), isNotActiveSelected, currentUserId)
    } else {
        adapter = MAkeDonarAdapter(this, filteredItems, isNotActiveSelected, currentUserId)
    }

        binding.Rcv2.adapter = adapter


    }

    //*******************************************************************************************************************************



}








































































