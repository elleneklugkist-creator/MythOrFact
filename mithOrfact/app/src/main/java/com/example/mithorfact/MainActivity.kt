package com.example.mithorfact

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// Screen 1: Welcome Screen - the entry point of the Life Hack or Urban Myth app
class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var btnStartQuiz: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        Log.d(TAG, "Welcome screen loaded")

        btnStartQuiz = findViewById(R.id.btnStartQuiz)

        // Navigate to the quiz screen when the user taps Start
        btnStartQuiz.setOnClickListener {
            Log.d(TAG, "Start button clicked - navigating to quiz screen")
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
