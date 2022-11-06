package com.cdpindia.demo_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class ServiceActivity : AppCompatActivity() {
    lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)

        button = findViewById(R.id.startService)

        Log.e("priyanka","Thread is=${Thread.currentThread()}")

        button.setOnClickListener {
            //start service
            var intent = Intent(this,ServiceClass::class.java)
            startService(intent)
        }

    }
}