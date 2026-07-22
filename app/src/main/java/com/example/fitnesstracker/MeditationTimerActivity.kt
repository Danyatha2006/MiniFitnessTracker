package com.example.fitnesstracker

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MeditationTimerActivity : AppCompatActivity() {
    private var timer: CountDownTimer? = null
    private lateinit var timerText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_meditation_timer)

        timerText = findViewById(R.id.timerText)
        val btnStart = findViewById<Button>(R.id.btnStartTimer)
        val btnStop = findViewById<Button>(R.id.btnStopTimer)
        val btnBack = findViewById<Button>(R.id.btnBack)

        btnStart.setOnClickListener {
            startTimer(600000) // 10 minutes
        }

        btnStop.setOnClickListener {
            timer?.cancel()
            timerText.text = "10:00"
        }

        btnBack.setOnClickListener {
            timer?.cancel()
            finish()
        }
    }

    private fun startTimer(millis: Long) {
        timer?.cancel()
        timer = object : CountDownTimer(millis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val mins = (millisUntilFinished / 1000) / 60
                val secs = (millisUntilFinished / 1000) % 60
                timerText.text = "%02d:%02d".format(mins, secs)
            }

            override fun onFinish() {
                timerText.text = "Finished!"
            }
        }.start()
    }
}