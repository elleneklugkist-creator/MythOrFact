package com.example.mithorfact

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// Screen 2: Flashcard Question Screen - loops through each question and records the user's answers
class MainActivity2 : AppCompatActivity() {

    private val TAG = "MainActivity2"

    private lateinit var btnFact: Button
    private lateinit var btnMyth: Button
    private lateinit var txtQuestion: TextView
    private lateinit var txtResult: TextView
    private lateinit var btnNext: Button

    private var currentQuestionIndex = 0
    private var score = 0
    private var questionAnswered = false

    // The five life hack or urban myth statements
    private val questions = arrayOf(
        "Putting wet electronics in rice will dry them out.",
        "A wooden spoon placed across a boiling pot prevents it from boiling over.",
        "Storing batteries in the fridge makes them last longer.",
        "Holding your car key fob to your chin increases its range.",
        "Rubbing a walnut on scratched wood furniture will hide the marks."
    )

    // Whether each statement is a real "Fact" (life hack) or a "Myth" (urban myth)
    private val correctAnswers = arrayOf(
        "Myth",
        "Fact",
        "Myth",
        "Fact",
        "Fact"
    )

    // Explanation shown after each answer so the user learns the reasoning
    private val explanations = arrayOf(
        "Rice does not absorb moisture effectively from electronics. Silica gel packs or simply air drying work far better.",
        "The wooden spoon breaks the surface tension of bubbles and stops the pot from boiling over. It really works!",
        "Cold temperatures drain most modern batteries faster. Store batteries at room temperature for best results.",
        "Your skull acts as an antenna and boosts the key fob signal slightly. Many drivers swear by this trick!",
        "The natural oils and pigment in a walnut fill small scratches and blend them into the wood grain."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        Log.d(TAG, "Quiz screen loaded")

        // Bind views from the layout
        btnFact = findViewById(R.id.btnFact)
        btnMyth = findViewById(R.id.btnMyth)
        txtQuestion = findViewById(R.id.txtQuestions)
        txtResult = findViewById(R.id.txtResult)
        btnNext = findViewById(R.id.btnNext)

        // Next is disabled until the user has answered the current question
        btnNext.isEnabled = false

        showQuestion()

        btnFact.setOnClickListener {
            Log.d(TAG, "User selected: Fact for question ${currentQuestionIndex + 1}")
            checkAnswer("Fact")
        }

        btnMyth.setOnClickListener {
            Log.d(TAG, "User selected: Myth for question ${currentQuestionIndex + 1}")
            checkAnswer("Myth")
        }

        btnNext.setOnClickListener {
            Log.d(TAG, "Next button clicked at question index $currentQuestionIndex")
            moveToNextQuestion()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Displays the current question and resets the answer state
    private fun showQuestion() {
        txtQuestion.text = "Q${currentQuestionIndex + 1} of ${questions.size}:\n\n${questions[currentQuestionIndex]}"
        txtResult.text = ""
        questionAnswered = false
        btnFact.isEnabled = true
        btnMyth.isEnabled = true
        btnNext.isEnabled = false
        Log.d(TAG, "Showing question ${currentQuestionIndex + 1}: ${questions[currentQuestionIndex]}")
    }

    // Evaluates the user's answer, shows feedback, and unlocks the Next button
    private fun checkAnswer(userAnswer: String) {
        // Prevent double-answering the same question
        if (questionAnswered) return
        questionAnswered = true

        val correctAnswer = correctAnswers[currentQuestionIndex]
        Log.d(TAG, "User answered: $userAnswer | Correct answer: $correctAnswer")

        // Disable answer buttons after the user has chosen
        btnFact.isEnabled = false
        btnMyth.isEnabled = false

        if (userAnswer == correctAnswer) {
            score++
            txtResult.text = "Correct! That's a real life hack!\n\n${explanations[currentQuestionIndex]}"
            Log.d(TAG, "Correct! Score is now $score")
        } else {
            txtResult.text = "Wrong! That's just an urban myth.\nCorrect answer: $correctAnswer\n\n${explanations[currentQuestionIndex]}"
            Log.d(TAG, "Wrong. Score remains $score")
        }

        // Change button label on the last question so the user knows what comes next
        if (currentQuestionIndex == questions.size - 1) {
            btnNext.text = "See Results"
        }
        btnNext.isEnabled = true
    }

    // Moves to the next question, or navigates to the Score Screen after the last one
    private fun moveToNextQuestion() {
        if (currentQuestionIndex < questions.size - 1) {
            currentQuestionIndex++
            showQuestion()
        } else {
            Log.d(TAG, "Quiz complete! Final score: $score / ${questions.size}")
            val intent = Intent(this, MainActivity3::class.java)
            // Pass score data and full question/answer arrays to the Score Screen
            intent.putExtra("score_key", score)
            intent.putExtra("total_key", questions.size)
            intent.putExtra("questions_key", questions)
            intent.putExtra("answers_key", correctAnswers)
            intent.putExtra("explanations_key", explanations)
            startActivity(intent)
        }
    }
}
