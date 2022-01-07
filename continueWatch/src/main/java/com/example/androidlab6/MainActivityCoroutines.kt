package com.example.androidlab6

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MainActivityCoroutines : AppCompatActivity() {
    companion object {
        const val STATE_SECONDS = "secondsElapsed"
    }

    private var secondsElapsed: Int = 0
    private lateinit var textSecondsElapsed: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("Activity", "is created")
        if (savedInstanceState != null) {
            with(savedInstanceState)  {
                secondsElapsed = getInt(STATE_SECONDS)
            }
        }
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                while (isActive) {
                    Log.d("Thread", "is running $secondsElapsed seconds")
                    textSecondsElapsed.text = getString(R.string.secondsElapsed, secondsElapsed++)
                    delay(1000)
                }
            }
        }
    }

    override fun onStart() {
        Log.d("Activity", "is started")
        super.onStart()
    }

    override fun onStop() {
        Log.d("Activity", "is stopped")
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
