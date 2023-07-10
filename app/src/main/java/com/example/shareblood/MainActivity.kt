package com.example.shareblood

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shareblood.Adapters.MAkeDonarAdapter
import com.example.shareblood.DataModel.DataModelDonarList
import com.example.shareblood.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //private  lateinit var searching:menu
    lateinit var binding: ActivityMainBinding
    var listthecatogories= arrayListOf<DataModelDonarList>()

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
                 // LocalDatabase
              listthecatogories= ArrayList<DataModelDonarList>()

        listthecatogories.add(DataModelDonarList("Jehad Akbar","swat","B","15"))
        listthecatogories.add(DataModelDonarList("Mansoor Shah ","batkhilah","B+","25"))
        listthecatogories.add(DataModelDonarList("Hasnain khan","chackdara","A+","26"))
        listthecatogories.add(DataModelDonarList("Waseem khan","Husband","o","30" ))
        listthecatogories.add(DataModelDonarList("Bilal Khan","Mangawara","B","22"))
        listthecatogories.add(DataModelDonarList("Aizaz Akbar","swat","B+","18"))
        listthecatogories.add(DataModelDonarList("Danish Akbar","malakand(Dir)","A","15"))
        listthecatogories.add(DataModelDonarList("Shehzad Akbar","swat","A-","16"))
          // work Adapter and recyclerview
        binding.RCV1.layoutManager= LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.RCV1.adapter=MAkeDonarAdapter(this,listthecatogories)

        //**********************************************************************************************************
                // search or find donar item
           binding.Toolbar.setOnMenuItemClickListener {
               when (it.itemId) {
                   R.id.searching-> {
                   this.startActivity(Intent(this,findDoar::class.java))
                       return@setOnMenuItemClickListener false
                   }
                   else -> { super.onOptionsItemSelected(it)}
               }
           }
        //************************************************************************************************************
             // MakeDonar eventHandler
             binding.makedonar.setOnClickListener {

                 val i = Intent(this, MakeDonar::class.java)
                     startActivity(i)
             }


           }





//*******************************************************************************

    }














