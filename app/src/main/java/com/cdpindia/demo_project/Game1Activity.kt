package com.cdpindia.demo_project

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider


class Game1Activity : AppCompatActivity() {
    lateinit var gridView: GridView
    lateinit var alphabet_list:ArrayList<String>
    lateinit var game1ActivityVM: Game1ActivityViewModel
    lateinit var result:TextView
    lateinit var click_item:TextView
    lateinit var matches_completed:TextView
    lateinit var no_of_attempt:TextView
    lateinit var clickList: ArrayList<TextView>
    lateinit var clickListData: ArrayList<String>
    var count = 0
    lateinit var Click1:TextView
    lateinit var Click2:TextView
    var variable1: String? = null
    var variable2: String? = null
    var successResultCount = 0
    var no_of_attempt_count = 0
    lateinit var view1:View
    lateinit var sharedpreferences:SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game1)

        game1ActivityVM = ViewModelProvider(this).get(Game1ActivityViewModel::class.java)
        sharedpreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

        clickList = arrayListOf()
        clickListData = arrayListOf()

        gridView = findViewById(R.id.grid_view)
        result = findViewById(R.id.result)
        matches_completed = findViewById(R.id.matches_completed)
        no_of_attempt = findViewById(R.id.no_of_attempt)

        //set value of matches_completed & no_of_attempt
        matches_completed.text = sharedpreferences.getInt("matches_completed",0).toString()
        no_of_attempt.text = sharedpreferences.getInt("no_of_attempt",0).toString()

        editor = sharedpreferences.edit()

        Observable()

        alphabet_list = arrayListOf("A","B","C","D","E","F","G","H","A","B","C","D","E","F","G","H")
        Log.e("priyanka","list is = $alphabet_list")
        var list:ArrayList<String> = alphabet_list.shuffled() as ArrayList<String>


        Log.e("priyanka","shuffled list is = $list")

        var adapter1 = GridAdapter(this, list)
        gridView.adapter = adapter1

        gridView.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->

            view1 = view
            Log.e("priyanka","listData = ${list[position]}")
            Log.e("priyanka","position is $position")

            click_item = view.findViewById(R.id.hide_text)

            if (count == 2){
                if (clickList.contains(view.findViewById(R.id.hide_text)))
                {
                    if (clickListData.get(0).equals(clickListData.get(1))){
                        result.text = "Card Matches"
                        result.setTextColor(Color.parseColor("#00FF00"))

                        editor.putInt("matches_completed",sharedpreferences.getInt("matches_completed",0)+1)

                        editor.putInt("no_of_attempt",sharedpreferences.getInt("no_of_attempt",0)+1)
                        editor.commit()

                        matches_completed.text = sharedpreferences.getInt("matches_completed",0).toString()
                        no_of_attempt.text = sharedpreferences.getInt("no_of_attempt",0).toString()
                    }
                    else{
                        result.text = "Card Does Not Matches"
                        result.setTextColor(Color.parseColor("#FF0000"))

                        editor.putInt("no_of_attempt",sharedpreferences.getInt("no_of_attempt",0)+1)
                        editor.commit()

                        no_of_attempt.text = sharedpreferences.getInt("no_of_attempt",0).toString()
                    }

                    Click1.visibility = View.GONE
                    Click2.visibility = View.GONE
                }

                else{
                    Click1.visibility = View.VISIBLE
                    Click2.visibility = View.VISIBLE
                    clickList.clear()
                    clickListData.clear()
                    count = 1
                    game1ActivityVM.count.value = true
                    game1ActivityVM.status.value = false
                }
            }
            else{
                if(!(clickList.contains(click_item)) && clickList.size < 2){
                    clickList.add(click_item)

                    //visible card
                    click_item.visibility = View.GONE

                    clickListData.add(view.findViewById<TextView>(R.id.text_view_data).text.toString())

                    count = count + 1
                    if (count != 2){
                        game1ActivityVM.status.value = false
                    }

                    if (count == 2 && clickList.size == 2){
                        Click1 = clickList.get(0)
                        Click2 = clickList.get(1)
                        variable1 = clickListData.get(0)
                        variable2 = clickListData.get(1)
                        Log.e("priyanka","variable1 $variable1")
                        Log.e("priyanka","variable2 $variable2")
                        checkResult(variable1,variable2)
                    }
                }
                else{
                    Toast.makeText(this,"You selected same card please select another card",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    private fun checkResult(click1: String?, click2: String?) {
        game1ActivityVM.status.value = true
        if (click1.equals(click2)){
            result.text = "Card Matches"
            result.setTextColor(Color.parseColor("#00FF00"))

            editor.putInt("matches_completed",sharedpreferences.getInt("matches_completed",0)+1)

            editor.putInt("no_of_attempt",sharedpreferences.getInt("no_of_attempt",0)+1)


            editor.commit()

            matches_completed.text = sharedpreferences.getInt("matches_completed",0).toString()
            no_of_attempt.text = sharedpreferences.getInt("no_of_attempt",0).toString()
        }
        else{
            result.text = "Card Does Not Matches"
            result.setTextColor(Color.parseColor("#FF0000"))
            editor.putInt("no_of_attempt",sharedpreferences.getInt("no_of_attempt",0)+1)
            editor.commit()

            no_of_attempt.text = sharedpreferences.getInt("no_of_attempt",0).toString()
        }
    }
    private fun Observable() {
     game1ActivityVM.count.observe(this, Observer {
         if (it){
             clickList.add(click_item)
             clickListData.add(view1.findViewById<TextView>(R.id.text_view_data).text.toString())
             click_item.visibility = View.GONE
         }
       })

        game1ActivityVM.status.observe(this, Observer {
            if (it){
                result.visibility = View.VISIBLE
            }
            else{
                result.visibility = View.GONE
            }

        })
    }
}