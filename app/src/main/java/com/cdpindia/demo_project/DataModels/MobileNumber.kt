package com.cdpindia.demo_project.DataModels

import com.google.firebase.firestore.PropertyName

data class MobileNumber (@get:PropertyName("country_code")
                         @set:PropertyName("country_code")
                         var countryCode: String="",
                         var number: String="")
