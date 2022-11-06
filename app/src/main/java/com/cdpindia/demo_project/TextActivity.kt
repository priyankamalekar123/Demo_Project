package com.cdpindia.demo_project
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.TimeUnit


class TextActivity : AppCompatActivity() {
    lateinit var textView: TextView
    lateinit var button: Button
    lateinit var button1: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text)

        textView = findViewById(R.id.hellotext)
        button = findViewById(R.id.btn)
        button1 = findViewById(R.id.btn1)
//        plusMinus()

        var handler1 = Handler(Looper.getMainLooper())

        @SuppressLint("HandlerLeak")
        val handler2: Handler = object : Handler() {
                override fun handleMessage(msg: Message) {
                    var bundle = msg.data
                    var data = bundle.getString("threadValue")
                    textView.text = data
                }
            }

        button1.setOnClickListener {
            Toast.makeText(this,"button click",Toast.LENGTH_SHORT).show()
        }

//        button.setOnClickListener{
//         val runnble:Runnable = object :Runnable {
//                  override fun run(){
//                      TimeUnit.SECONDS.sleep(5)
//                      var msg = handler2.obtainMessage()
//                      var bundle = Bundle()
//                      bundle.putString("threadValue","Thread completed")
//                      msg.data = bundle
//                      handler2.sendMessage(msg)
//                      Handler(Looper.getMainLooper()).post{Toast.makeText(applicationContext,"background thread operation is completed",Toast.LENGTH_SHORT).show()}
//                  }
//         }
//            var thread = Thread(runnble)
//            thread.start()
//        }

        button.setOnClickListener {
            TimeUnit.SECONDS.sleep(5)
            runOnUiThread{
                textView.text = "Thread completed"
            }
        }



    }
//    fun plusMinus() {
//        // Write your code here
//        var arrayOfNo = arrayListOf(1,2,7,6,0,-1,-3,0,-9)
//        var arrayOfPositiveNo = arrayListOf<Int>()
//        var arrayOfNegativeNo = arrayListOf<Int>()
//        var arrayOfNutralNo = arrayListOf<Int>()
//        var arraySize = arrayOfNo.size
//        Log.e("priyanka","arraySize = $arraySize")
//        for(i in arrayOfNo){
//            if(i>0){
//                arrayOfPositiveNo.add(i)
//            }
//            else if(i<0){
//                arrayOfNegativeNo.add(i)
//            }
//            else{
//                arrayOfNutralNo.add(i)
//            }
//        }
//        var r1: Double = ((arrayOfPositiveNo.size).toDouble() / (arraySize).toDouble())
//        var r2: Double = (arrayOfNegativeNo.size / arraySize).toDouble()
//        var r3: Double = (arrayOfNutralNo.size / arraySize).toDouble()
//        Log.e("priyanka","positive = ${arrayOfPositiveNo.size}")
//        Log.e("priyanka","negative = ${arrayOfNegativeNo.size}")
//        Log.e("priyanka","nutral = ${arrayOfNutralNo.size}")
//        Log.e("priyanka","PositiveNoRatio = $r1")
//        Log.e("priyanka","NegativeNoRatio = $r2")
//        Log.e("priyanka","NutralNoRatio = $r3")
//
//    }





}