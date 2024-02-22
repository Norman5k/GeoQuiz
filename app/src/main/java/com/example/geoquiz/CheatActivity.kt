@file:Suppress("DEPRECATION")

package com.example.geoquiz

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
private const val EXTRA_ANSWER_IS_TRUE = "com.example.geoquiz.answer"
const val EXTRA_ANSWER_SHOWN = "com.example.geoquiz.answer_shown"

class CheatActivity : AppCompatActivity() {
    private lateinit var answerTextView: TextView
    private lateinit var versionTextView: TextView
    private lateinit var showAnswerButton: Button
    private var answerIsTrue = false
    private val cheatViewModel : CheatViewModel by lazy {
        ViewModelProviders.of(this)[CheatViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        answerTextView = findViewById(R.id.answer_text_view)
        versionTextView = findViewById(R.id.version_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)

        versionTextView.text = getString(R.string.api_level, Build.VERSION.SDK_INT)

        if (cheatViewModel.isCheater) {
            answerTextView.setText(if (answerIsTrue) R.string.answer_true else R.string.answer_false)
            setAnswerShownResult()
        }

        showAnswerButton.setOnClickListener {
            answerTextView.setText(if (answerIsTrue) R.string.answer_true else R.string.answer_false)
            setAnswerShownResult()
        }
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
        }
    }

    private fun setAnswerShownResult() {
        cheatViewModel.isCheater = true
        val intent = Intent().putExtra(EXTRA_ANSWER_SHOWN, true)
        setResult(RESULT_OK, intent)
    }
}