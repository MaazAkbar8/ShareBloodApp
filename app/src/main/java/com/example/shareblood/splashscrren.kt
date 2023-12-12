package com.example.shareblood

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import java.util.logging.Handler

class splashscrren : AppCompatActivity() {
    companion object {
        private const val SPLASH_DELAY = 2000L // 2 seconds
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscrren)



        android.os.Handler().postDelayed({
            if (isUserRegistered()) {
                // User is registered, navigate to MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                navigateToLoginFragment()
            }

        }, SPLASH_DELAY)
    }

    private fun navigateToLoginFragment() {
        try {
             val i=Intent(this,LoginScreen::class.java)
            startActivity(i)
            finish()
        } catch (e: Exception) {
            e.printStackTrace()
            // Handle the exception (e.g., log it or show a message)
        }
    }

    private fun isUserRegistered(): Boolean {
        val sharedPrefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPrefs.getBoolean("isRegistered", false)

    }


}