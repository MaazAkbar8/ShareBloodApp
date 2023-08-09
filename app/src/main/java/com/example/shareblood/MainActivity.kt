package com.example.shareblood

import android   .annotation.SuppressLint
import android.content.ClipData
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shareblood.Adapters.MAkeDonarAdapter
import com.example.shareblood.DataModel.DataModelDonorList
import com.example.shareblood.databinding.ActivityMainBinding
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {





    private lateinit var binding: ActivityMainBinding
    private lateinit var List: ArrayList<DataModelDonorList>
    private lateinit var adapter: MAkeDonarAdapter


    // data stored and retrieve need to these line
    private var db = Firebase.firestore
    private lateinit var database: DatabaseReference


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // intialization
// two lne new

          // adapter = MAkeDonarAdapter(usersList)
        db = FirebaseFirestore.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("usersList")
        binding.Rcv1.layoutManager = LinearLayoutManager(this)
        binding.Rcv1.setHasFixedSize(true)
        List = ArrayList()

       adapter = MAkeDonarAdapter(this,List)
        binding.Rcv1.adapter = adapter









//***********************************************************************************************************************************
        // Get data from database in display Recyclerview





            db.collection("user").get().addOnSuccessListener {
                if (!it.isEmpty) {
                    for (data in it.documents) {
                        val users: DataModelDonorList? =
                            data.toObject(DataModelDonorList::class.java)
                        if (users != null) {
                            List.add(users)
                        }

                        // thers adapter class
                        binding.Rcv1.adapter = MAkeDonarAdapter(this,List)
                    }
                }
            }.addOnFailureListener {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }
        //**********************************************************************************************************
        // ambulance Activity
        binding.Toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.embulance -> {
                    this.startActivity(Intent(this, AmbulanceActivity::class.java))
                    return@setOnMenuItemClickListener false
                }
                else -> {
                    super.onOptionsItemSelected(it)
                }
            }
        }
//*******************************************************************************************************************
        binding.makedonar.setOnClickListener {
            val i = Intent(this, MakeDonar::class.java)
            startActivity(i)
        }


        //***********************************************************************************************************************

        // Edittext work
        binding.etsearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                filterItems(s.toString())

            }
        })


        // Fetch data from Firebase and update RecyclerView
        fetchItemsFromFirebase()







    }






    //oncretae end




    // *************************************************************************************************************************
    private fun fetchItemsFromFirebase() {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    List.clear()
                    for (itemSnapshot in snapshot.children) {
                        val item = itemSnapshot.getValue(ClipData.Item::class.java)
                        item?.let {
                            // list.add datamodelDonarlist
                            List.add(DataModelDonorList())
                        }
                    }
                    adapter.notifyDataSetChanged()
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
        val filteredItems = List.filter {
            it.name!!.contains(query, ignoreCase = true) ||
                    it.age!!.contains(query, ignoreCase = true) ||
                    (it.city!!.contains(query, ignoreCase = true) ||
                            it.bloodGroup!!.contains(query, ignoreCase = true))
        }
                    adapter = MAkeDonarAdapter(this,filteredItems)

                    binding.Rcv1.adapter = adapter

    }
    // new work




}


        // newly works today


        //****************************************************************************************************************************End****





































































