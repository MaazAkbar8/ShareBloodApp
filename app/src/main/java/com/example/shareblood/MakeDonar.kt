package com.example.shareblood

import android.annotation.SuppressLint
import android.content.ClipData.Item
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MakeDonar : AppCompatActivity() {
    private lateinit var postdonar: TextView
    private lateinit var searching:Item
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_donar)

        postdonar=findViewById(R.id.tvpostdonar)


        postdonar.setOnClickListener {
            val i=Intent(this,MainActivity::class.java)
            startActivity(i)
        }



    }
}