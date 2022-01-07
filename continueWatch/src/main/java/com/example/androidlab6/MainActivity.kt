package com.example.androidlab6

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    companion object {
        const val STATE_SECONDS = "secondsElapsed"
    }

    private var secondsElapsed: Int = 0
    private lateinit var textSecondsElapsed: TextView
    private lateinit var backgroundThread: Thread

    @Volatile
    private var isRunning = false

    private val runnable = Runnable {
        while (isRunning) {
            Log.d("Thread", "is running $secondsElapsed seconds")
            textSecondsElapsed.post {
                textSecondsElapsed.text =
                    getString(R.string.secondsElapsed, secondsElapsed++)
            }
            Thread.sleep(1000)
        }
        Log.d("Thread", "is stopped")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            with(savedInstanceState)  {
                secondsElapsed = getInt(STATE_SECONDS)
            }
        }
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        Log.d("Activity", "is created")
    }

    override fun onStart() {
        Log.d("Activity", "is resumed")
        if (!isRunning) {
            isRunning = true
            backgroundThread = Thread(runnable)
            backgroundThread.start()
        }
        super.onStart()
    }

    override fun onStop() {
        Log.d("Activity", "is paused")
        isRunning = false
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(STATE_SECONDS, secondsElapsed)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.getInt(STATE_SECONDS)
        super.onRestoreInstanceState(savedInstanceState)
    }
}