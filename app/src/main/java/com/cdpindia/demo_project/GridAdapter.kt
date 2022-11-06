package com.cdpindia.demo_project

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.BaseAdapter
import android.widget.TextView

class GridAdapter (var context: Context,var list:ArrayList<String>):BaseAdapter() {

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        var view1  = view
        if (view1 == null){
            // inflate the layout for each list row
            view1 = LayoutInflater.from(context).inflate(R.layout.grid_layout_item,parent,false)
        }

        // get the TextView for item name
        var textview = view1?.findViewById<TextView>(R.id.text_view_data)

        // get current item to be displayed
        var list_item = getItem(position)

        textview?.text = list_item.toString()

        //return the view for current row
        return view1!!

    }
}