package com.example.examenparciallp_kevin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

class AuthActivity : AppCompatActivity() {
    private lateinit var loginButton : Button
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        FirebaseApp.initializeApp(this)

        Thread.sleep(2000)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)



        //Analytics

        init()
        setup()
    }

    private fun setup(){
    title = "Mi tiendita"

        loginButton.setOnClickListener {
        if(emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()){
            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(emailEditText.text.toString(), passwordEditText.text.toString())
                .addOnCompleteListener{
                    if(it.isSuccessful){
                        showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                    }else{
                        showAlert()
                    }
                }
        }

        }

    }
    private fun showAlert(){
            val builder = AlertDialog.Builder(this)
            builder.setTitle("error")
            builder.setMessage("se ha producido un error autentificando al usuario")
            builder.setPositiveButton("aceptar", null)
            val dialog: AlertDialog = builder.create()
        dialog.show()


    }
    private fun showHome(email : String, provider : ProviderType){
        val homeItent = Intent(this, HomeActivity::class.java).apply {
            putExtra( "email", email)
            putExtra( "provider", provider.name)
        }
        startActivity(homeItent)
    }


    //setup
    private fun init() {
       loginButton = findViewById(R.id.loginButton)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

    }

}