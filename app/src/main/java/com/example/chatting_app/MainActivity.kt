package com.example.chatting_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentUser = Firebase.auth.currentUser

        if (currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}