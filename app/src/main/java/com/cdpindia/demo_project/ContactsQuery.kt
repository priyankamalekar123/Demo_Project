package com.cdpindia.demo_project

import android.util.Log
import com.cdpindia.demo_project.DataModels.UserProfile
import com.google.firebase.firestore.FirebaseFirestore

class ContactsQuery {

    companion object{
        var queriedList=ArrayList<UserProfile>()
        var currentQueryCount=0
        var totalQueryCount=0
    }

    private val usersCollection = FirebaseFirestore.getInstance().collection("Users")

    fun makeQuery(position: Int,listOfMobile: ArrayList<String>,listener: QueryListener) {
        try {
            listener.onStart(position,listOfMobile)
            usersCollection.whereIn("mobile.number", listOfMobile).get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val contact = document.toObject(UserProfile::class.java)
                        queriedList.add(contact)
                    }
                    currentQueryCount += 1
                    if(currentQueryCount == totalQueryCount)
                        listener.onCompleted(queriedList)
                }
                .addOnFailureListener { exception ->
                    Log.e("priyanka","Error ${exception.message}")
                    currentQueryCount += 1
                    if(currentQueryCount == totalQueryCount)
                        listener.onCompleted(queriedList)
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

interface QueryListener{
    fun onCompleted(queriedList: ArrayList<UserProfile>)
    fun onStart(position: Int,contactBatch: ArrayList<String>)
}