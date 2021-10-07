package com.example.examenparciallp_kevin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

enum class ProviderType {
    BASIC
}

class HomeActivity : AppCompatActivity() {
    private lateinit var myProductButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        myProductButton = findViewById(R.id.MyProductButton)


        setup();
        myProductButton.setOnClickListener{
            startActivity(Intent(this, ProductoActivity:: class.java))
        }
    }



        private fun setup(){



        }


}