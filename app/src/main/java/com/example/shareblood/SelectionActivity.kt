package com.example.shareblood

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shareblood.databinding.ActivitySelectionBinding

class SelectionActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySelectionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)





        binding.AsADonnar.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            intent.putExtra("SHOW_RADIO_GROUP", true)
            startActivity(intent)
            finish()
        }
        binding.AsAAmbulance.setOnClickListener {
            val intent= Intent(this,AmbulanceActivity::class.java)
            intent.putExtra("SHOW_RADIO_GROUP", true)
            startActivity(intent)
            finish()
        }
    }
}