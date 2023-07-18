package com.example.shareblood

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shareblood.Adapters.MAkeDonarAdapter
import com.example.shareblood.DataModel.DataModelDonorList
import com.example.shareblood.databinding.ActivityMainBinding
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    //private  lateinit var searching:menu
  lateinit var recyclerView:  RecyclerView
    lateinit var binding: ActivityMainBinding
   private lateinit var list:ArrayList<DataModelDonorList>

    // data stored and retrieve need to these line
    private var db= Firebase.firestore

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



       // intialization
       recyclerView=findViewById(R.id.Rcv1)
        db= FirebaseFirestore.getInstance()
       recyclerView.layoutManager=LinearLayoutManager(this)
       list= arrayListOf()
//***********************************************************************************************************************************


                    // Get data from database in display Recyclerview

                db.collection("user").get().addOnSuccessListener {
                    if (!it.isEmpty) {
                        for (data in it.documents) {
                            val users: DataModelDonorList? = data.toObject(DataModelDonorList::class.java)
                            if (users != null) {
                                list.add(users)
                            }
                        }

                        recyclerView.adapter= MAkeDonarAdapter(list)

                    }

                }.addOnFailureListener {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()

                }

        //**********************************************************************************************************
        // search or find donar item
        binding.Toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.searching -> {
                    this.startActivity(Intent(this, findDoar::class.java))
                    return@setOnMenuItemClickListener false
                }
                else -> {
                    super.onOptionsItemSelected(it)
                }
            }
        }








       //************************************************************************************************************





        // MakeDonar eventHandler
            binding.makedonar.setOnClickListener {
                 val i= Intent(this, MakeDonar::class.java)
                startActivity(i)
            }

    }


}




//*******************************************************************************


















