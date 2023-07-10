package com.example.shareblood

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class findDoar : AppCompatActivity() {
    private lateinit var Add:TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_doar)
        Add=findViewById(R.id.tvAdd)

        Add.setOnClickListener {
            val i= Intent(this,findDonarList::class.java)
            startActivity(i)
        }
    }

}