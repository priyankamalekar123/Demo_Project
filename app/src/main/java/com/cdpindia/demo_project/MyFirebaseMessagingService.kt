package com.cdpindia.demo_project

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService

class MyFirebaseMessagingService: FirebaseMessagingService(){

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.e("priyanka","FCM_Token = $token")
    }
}