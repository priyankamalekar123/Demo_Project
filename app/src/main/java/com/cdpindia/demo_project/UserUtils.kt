package com.cdpindia.demo_project

import android.util.Log
import com.cdpindia.demo_project.DataModels.UserProfile

object UserUtils {

    fun queryContacts() {
        val listOfContacts= mutableListOf<String>()
        listOfContacts.add("9500552211")
        listOfContacts.add("9500552212")
        listOfContacts.add("9500552213")
        listOfContacts.add("9500552214")
        listOfContacts.add("9500552215")
        listOfContacts.add("9500552216")
        listOfContacts.add("9500552217")
        listOfContacts.add("9500552218")
        listOfContacts.add("9500552219")
        listOfContacts.add("9500552210")
        listOfContacts.add("9500552221")
        listOfContacts.add("9500552220")

        val subLists: List<List<String>> = listOfContacts.chunked(size = 10)
        ContactsQuery.totalQueryCount=subLists.size
        val contactQuery= ContactsQuery()
        for(index in subLists.indices)
            contactQuery.makeQuery(index,ArrayList(subLists[index]), onQueryListener)
    }

    private val onQueryListener=object : QueryListener {
        override fun onCompleted(queriedList: ArrayList<UserProfile>) {
            //handle the result
        }

        override fun onStart(position: Int, contactBatch: ArrayList<String>) {
            Log.e("priyanka","onQueryStart pos : $position inputs: ${contactBatch.size}")
        }
    }

}