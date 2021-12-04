package com.example.task2

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.net.URL

class MainActivityCoroutines : AppCompatActivity() {
    private val urlStr = "https://cdn.vox-cdn.com/thumbor/HWPOwK-35K4Zkh3_t5Djz8od-jE=/0x86:1192x710/fit-in/1200x630/cdn.vox-cdn.com/uploads/chorus_asset/file/22312759/rickroll_4k.jpg"
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button).setOnClickListener {
            Log.d("button", "clicked")
            imageView = findViewById(R.id.imageView)
            it.isClickable = false
            MainScope().launch(Dispatchers.IO) {
                val urlImg = URL(urlStr)
                val imageBitmap = BitmapFactory.decodeStream(urlImg.openConnection().getInputStream())
                imageView.post {
                    imageView.setImageBitmap(imageBitmap)
                }
                Log.d("Image", "Downloaded")
            }
        }
    }
}