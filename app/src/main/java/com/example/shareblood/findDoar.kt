package com.example.shareblood

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.shareblood.databinding.ActivityFindDoarBinding

class findDoar : AppCompatActivity() {

    private lateinit var binding:ActivityFindDoarBinding
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFindDoarBinding.inflate(layoutInflater)
        setContentView(binding.root)



         // tvAdd button eventhandling
       binding.tvAdd.setOnClickListener {
            val i= Intent(this,findDonarList::class.java)
            startActivity(i)
        }

        //****************************************************************************************
        // Email dropdownlist work

        val list= listOf("Timergara","Balambat" ,"Lal_Qilla" ,"Jandol" ,"Adenzai" ,"Samar Bagh", "Chakdara","Munda","Maidan","Talash")
        val adapter= ArrayAdapter(this,R.layout.list_item_city,list)
        binding.dropdownfield.setAdapter(adapter)

        //*************************************************************************************

        // Blood type works

        // Bloodgroup dropdownlist works

        val m= listOf(" (A+)", " (A-)", "(B+) ","(B-) "," (AB+)", "(AB-) "," (O+) ","(O-)")

        val Adapter= ArrayAdapter(this,R.layout.list_item_bloodtype,m)
        binding.dropdownfieldBloodg.setAdapter(Adapter)
        //*****************************************************************************************
    }

}