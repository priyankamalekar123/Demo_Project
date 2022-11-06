package com.cdpindia.demo_project

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.Button
import android.widget.TextView
import java.util.concurrent.TimeUnit

class MultithreadingActivity : AppCompatActivity() {
    lateinit var textView: TextView
    lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multithreading)

        textView = findViewById(R.id.multiThredText)
        button= findViewById(R.id.multiThredBtn)


        val handler: Handler = @SuppressLint("HandlerLeak")
        object : Handler(mainLooper) {
            override fun handleMessage(msg: Message) {
                var bundle = msg.data
                var data1 = bundle.getString("thread1_Value")
                textView.text = data1

                var data2 = bundle.getString("thread2_Value")
                textView.text = data2

                var data3 = bundle.getString("thread3_Value")
                textView.text = data3
            }
        }


        button.setOnClickListener {

            //first Thread
            val thread1 = Thread(Runnable {
                Log.e("priyanka","Thread 1 is start")
                TimeUnit.SECONDS.sleep(5)
                Log.e("priyanka","Thread 1 is end")
                var msg1 = handler.obtainMessage()
                var bundle = Bundle()
                bundle.putString("thread1_Value","Thread 1 start")
                msg1.data = bundle
                handler.sendMessage(msg1)
            })
            //Second Thread
            val thread2 = Thread(Runnable {
                Log.e("priyanka","Thread 2 is start")
                TimeUnit.SECONDS.sleep(5)
                Log.e("priyanka","Thread 2 is end")
                var msg2 = handler.obtainMessage()
                var bundle = Bundle()
                bundle.putString("thread2_Value","Thread 2 start")
                msg2.data = bundle
                handler.sendMessage(msg2)
            })
            //Second Thread
            val thread3 = Thread(Runnable {
                Log.e("priyanka","Thread 3 is start")
                TimeUnit.SECONDS.sleep(5)
                Log.e("priyanka","Thread 3 is end")
                var msg3 = handler.obtainMessage()
                var bundle = Bundle()
                bundle.putString("thread3_Value","Thread 3 start")
                msg3.data = bundle
                handler.sendMessage(msg3)
            })

            //First thread execute
            thread1.start()
            thread1.join()
            TimeUnit.SECONDS.sleep(5)

            //Second thread execute
            if (!thread1.isAlive){
                thread2.start()
            }
            thread2.join()
            TimeUnit.SECONDS.sleep(5)

            //Third thread execute
            if (!thread2.isAlive){
                thread3.start()
            }




        }
    }
}