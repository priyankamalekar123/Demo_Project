package com.cdpindia.demo_project.DataModels

import androidx.room.PrimaryKey

data class ChatUser(@PrimaryKey
                    var id: String,var localName: String,var user: UserProfile, var messageDocId: String="",
                    var locallySaved: Boolean=false,
                    var unRead: Int=0)
