package com.example.shareblood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shareblood.DataModel.DataModelDonorList
import com.example.shareblood.databinding.ActivityFindDonarListBinding

class findDonarList : AppCompatActivity() {
    private lateinit var binding: ActivityFindDonarListBinding
    var data = arrayListOf<DataModelDonorList>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindDonarListBinding.inflate(layoutInflater)
        setContentView(binding.root)
//*******************************************************************************************************************
        //local database
/*
        data= ArrayList<DataModelDonarList>()

        data.add(DataModelDonarList("hamza khan","Temergara","A+","15"))
        data.add(DataModelDonarList("sohail Shah ","Temergara","A+","25"))
        data.add(DataModelDonarList("qasim khan","Temergara","A+","26"))
        data.add(DataModelDonarList("soahib khan","Temergara","A+","30" ))
        data.add(DataModelDonarList("jawad Khan","Temergara","A+","22"))
        data.add(DataModelDonarList("sartaj khan","Temergara","A+","18"))
        data.add(DataModelDonarList("Tufail khan","Temergara","A+","15"))
        data.add(DataModelDonarList("seraj akmal","Temergara","A+","16"))
        // work Adapter and recyclerview
        binding.RCV2.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        binding.RCV2.adapter= MAkeDonarAdapter(this,data)

//********************************************************************************************************************


    }
    */
 */
    }
}