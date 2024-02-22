package com.example.geoquiz

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"

class Main1Activity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView: TextView
    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this)[QuizViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex

        setContentView(R.layout.activity_main)
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.text_question)

        trueButton.setOnClickListener {
            checkAnswer(true)
            updateClickable(false)
        }
        falseButton.setOnClickListener {
            checkAnswer(false)
            updateClickable(false)
        }
        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
            updateClickable(true)
        }
        questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
            updateClickable(true)
        }
        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy()")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState")
        outState.putInt(
            KEY_INDEX,
            quizViewModel.currentIndex
        )
    }

    private fun updateClickable(isClickable: Boolean) {
        trueButton.isClickable = isClickable
        falseButton.isClickable = isClickable
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer

        val textResId = if (userAnswer == correctAnswer) R.string.correct_toast
        else R.string.incorrect_toast
        Toast.makeText(
            this,
            textResId,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }
}