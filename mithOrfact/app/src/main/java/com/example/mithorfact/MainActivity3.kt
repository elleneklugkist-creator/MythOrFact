package com.example.mithorfact

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// Screen 3: Score Screen - shows the final score, personalised feedback, and a full review
class MainActivity3 : AppCompatActivity() {

    private val TAG = "MainActivity3"

    private lateinit var txtFinalScore: TextView
    private lateinit var txtFeedback: TextView
    private lateinit var txtReview: TextView
    private lateinit var btnRestart: Button
    private lateinit var btnExit: Button
    private lateinit var btnShowReview: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main3)

        Log.d(TAG, "Score screen loaded")

        // Bind views from the layout
        txtFinalScore = findViewById(R.id.txtFinalScore)
        txtFeedback = findViewById(R.id.txtFeedback)
        txtReview = findViewById(R.id.txtReview)
        btnRestart = findViewById(R.id.btnRestart)
        btnExit = findViewById(R.id.btnExit)
        btnShowReview = findViewById(R.id.btnShowReview)

        // Retrieve the score and question data passed from Screen 2
        val score = intent.getIntExtra("score_key", 0)
        val total = intent.getIntExtra("total_key", 5)
        val questions = intent.getStringArrayExtra("questions_key") ?: arrayOf()
        val answers = intent.getStringArrayExtra("answers_key") ?: arrayOf()
        val explanations = intent.getStringArrayExtra("explanations_key") ?: arrayOf()

        Log.d(TAG, "Score received: $score / $total")

        txtFinalScore.text = "You scored $score out of $total!"

        // Personalised feedback based on how many questions the user got right
        val feedback = when {
            score == total       -> "Master Hacker! Perfect score - you know your stuff!"
            score >= total * 0.8 -> "Hack Savvy! Almost perfect - great work!"
            score >= total * 0.6 -> "Pretty Sharp! A few myths slipped through."
            score >= total * 0.4 -> "Getting There! Keep practising those hacks."
            else                 -> "Stay Safe Online! Study up and try again."
        }
        txtFeedback.text = feedback
        Log.d(TAG, "Feedback shown: $feedback")

        // Review list is hidden until the user taps Show Review
        txtReview.visibility = View.GONE

        btnShowReview.setOnClickListener {
            Log.d(TAG, "Review button tapped - building review list")

            // Build a summary of every question, its correct answer, and the explanation
            val reviewBuilder = StringBuilder()
            for (i in questions.indices) {
                reviewBuilder.append("Q${i + 1}: ${questions[i]}\n")
                reviewBuilder.append("Answer: ${answers[i]}\n")
                if (i < explanations.size) {
                    reviewBuilder.append("${explanations[i]}\n")
                }
                reviewBuilder.append("\n")
            }

            txtReview.text = reviewBuilder.toString()
            txtReview.visibility = View.VISIBLE
            // Disable the button once the review is already visible
            btnShowReview.isEnabled = false
        }

        // Restart takes the user back to the beginning of the quiz
        btnRestart.setOnClickListener {
            Log.d(TAG, "Restart button clicked - returning to quiz screen")
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
            finish()
        }

        // Exit closes the entire app
        btnExit.setOnClickListener {
            Log.d(TAG, "Exit button clicked - closing app")
            finishAffinity()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
