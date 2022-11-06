package com.cdpindia.demo_project

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log


class ServiceClass: Service() {

    var randomNumber:Int = 0
    var isGenerate:Boolean = false
    var MAX = 100
    var MIN = 1

    class MyServiceBinder : Binder() {
        private fun getservice():ServiceClass{
            return ServiceClass()
        }
    }
    var mIBinder:IBinder = MyServiceBinder()
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("priyanka","Thread is in service=${Thread.currentThread()}")
        isGenerate = true
        Thread{
            startRandomNumberGenerator()
        }.start()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopRandomNumberGenerator()
        Log.e("priyanka","service stop")
    }

    override fun onBind(p0: Intent?): IBinder? {
        return mIBinder
    }

    fun startRandomNumberGenerator(){
        if (isGenerate){
            randomNumber = (1..20).random()
            Log.e("priyanka","randomNumber $randomNumber")
        }
    }
    fun stopRandomNumberGenerator(){
        isGenerate = false
    }

}