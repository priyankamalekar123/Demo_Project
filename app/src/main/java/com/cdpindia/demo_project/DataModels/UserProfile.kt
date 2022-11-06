package com.cdpindia.demo_project.DataModels

import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class UserProfile(var uId: String,
                       @ServerTimestamp
                       var createdAt: Date?=null,
                       @ServerTimestamp
                       var updatedAt: Date?=null,
                       @get:PropertyName("user_name")
                       @set:PropertyName("user_name")
                       var userName: String="",
                       var token :String="",
                       var mobile: MobileNumber?=null)
