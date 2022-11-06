package com.cdpindia.demo_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class GameActivity : AppCompatActivity() {
    lateinit var alphabet_list:ArrayList<String>
    lateinit var shuffle_list:ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        alphabet_list = arrayListOf("A","B","C","D","E","F","G","H","A","B","C","D","E","F","G","H")
        Log.e("priyanka","list is = $alphabet_list")
        var list = alphabet_list.shuffled()
        Log.e("priyanka","shuffled list is = $list")

    }
}