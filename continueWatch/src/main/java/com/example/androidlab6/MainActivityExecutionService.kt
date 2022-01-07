package com.example.androidlab6

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

class MainActivityExecutionService : AppCompatActivity() {
    companion object {
        const val STATE_SECONDS = "secondsElapsed"
    }

    private var secondsElapsed: Int = 0
    private lateinit var textSecondsElapsed: TextView
    private lateinit var executorService: Future<*>

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
        Log.d("Activity", "is started")
        executorService = (application as ExecutorApp).executor.submit {
            while (!executorService.isCancelled) {
                Log.d("Thread", "is running $secondsElapsed seconds")
                textSecondsElapsed.post {
                    textSecondsElapsed.text =
                        getString(R.string.secondsElapsed, secondsElapsed++)
                }
                Thread.sleep(1000)
            }
        }
        super.onStart()
    }

    override fun onStop() {
        Log.d("Activity", "is stopped")
        super.onStop()
        executorService.cancel(true)
        if (executorService.isCancelled)
            Log.d("Thread", "cancelled")
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

class ExecutorApp : Application() {
    var executor: ExecutorService = Executors.newSingleThreadExecutor()
}