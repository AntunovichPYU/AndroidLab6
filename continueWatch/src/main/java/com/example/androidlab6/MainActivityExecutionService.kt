package com.example.androidlab6

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivityExecutionService : AppCompatActivity() {
    companion object {
        const val STATE_SECONDS = "secondsElapsed"
    }

    private var secondsElapsed: Int = 0
    private lateinit var textSecondsElapsed: TextView
    private lateinit var executorService: ExecutorService

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

    override fun onResume() {
        Log.d("Activity", "is resumed")
        executorService = Executors.newSingleThreadExecutor()
        executorService.execute {
            while (!executorService.isShutdown) {
                Log.d("Thread", "is running")
                textSecondsElapsed.post {
                    textSecondsElapsed.text =
                        getString(R.string.secondsElapsed, secondsElapsed++)
                }
                Thread.sleep(1000)
            }
        }
        super.onResume()
    }

    override fun onPause() {
        Log.d("Activity", "is paused")
        super.onPause()
        executorService.shutdown()
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
