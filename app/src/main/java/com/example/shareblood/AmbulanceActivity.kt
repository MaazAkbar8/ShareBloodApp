package com.example.shareblood

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.shareblood.Adapters.AddEmbulnceAdapter
import com.example.shareblood.Adapters.MAkeDonarAdapter
import com.example.shareblood.DataModel.AddEmmbulncDataModel
import com.example.shareblood.DataModel.DataModelDonorList
import com.example.shareblood.databinding.ActivityAmbulanceBinding
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AmbulanceActivity : AppCompatActivity() {

    lateinit var binding: ActivityAmbulanceBinding
    private lateinit var List2: ArrayList<AddEmmbulncDataModel>
    private lateinit var adapter2: AddEmbulnceAdapter
    private var db2 = Firebase.firestore
    private lateinit var database: DatabaseReference
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
        adapter2 = AddEmbulnceAdapter(this,List2)
        binding.Rcv .adapter = adapter2


        // Get data from database in display Recyclerview

        db2.collection("Ambulancess").get().addOnSuccessListener {
            if (!it.isEmpty) {
                for (data in it.documents) {
                    val ambulance: AddEmmbulncDataModel? =
                        data.toObject(AddEmmbulncDataModel::class.java)
                    if (ambulance != null) {
                        List2.add(ambulance)
                    }

                    // thers adapter class
                    binding.Rcv.adapter = AddEmbulnceAdapter(this,List2)
                }
            }
        }.addOnFailureListener {
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        }

             // search edittext if a user search value in edittext to display all related data in recyclerview


        // Edittext work
        binding.citysearch .addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                filterItems(s.toString())

            }
        })


        // bloodtext work
        binding.bloodtypesearch .addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                filterItems(s.toString())

            }
        })

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


    // *************************************************************************************************************************
    private fun fetchItemsFromFirebase() {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    List2.clear()
                    for (itemSnapshot in snapshot.children) {
                        val item = itemSnapshot.getValue(ClipData.Item::class.java)
                        item?.let {
                            // list.add datamodelDonarlist
                            List2.addAll( listOf(AddEmmbulncDataModel()))
                        }
                    }
                    adapter2.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database read error
            }
        })
    }
    //******************************************************************************

    // this work is suposee user put name or city or bloodgroup or age is display these related all data in recyclerview
    private fun filterItems(query: String) {
        val filteredItems = List2.filter {
            it.city!!.contains(query, ignoreCase = true) ||
                    it.hospital!!.contains(query, ignoreCase = true) ||
                    (it.RegNo!!.contains(query, ignoreCase = true))

        }
        adapter2 = AddEmbulnceAdapter(this,filteredItems)

       // binding.include1.Rcv1.adapter = adapter2

    }



    }
