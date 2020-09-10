package com.example.finalgame

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.fragment.app.FragmentActivity

/*
Ke Zhao
CIS 195 Homework
 */

data class ListViewAdapter (var activity: FragmentActivity?, var playerList: List <PlayerStat>):
    BaseAdapter() {
        override fun getItem (p0: Int): Any {
            return playerList.get(p0)
        }
    override fun getItemId(p0:Int):Long{
        return p0.toLong()
    }
    override fun getCount(): Int{
        return playerList.size
    }
    private class ViewHolder(view: View){
        var name: TextView = view.findViewById(R.id.user_name)
        var score: TextView = view.findViewById(R.id.score)
    }
    override fun getView (position:Int, convertView: View?, parent: ViewGroup?): View{
        var viewHolder: ViewHolder
        var convertView = convertView

        if(convertView == null){
            convertView=LayoutInflater.from(parent?.context).inflate(R.layout.player_item, parent,false)
            viewHolder=ViewHolder(convertView)
            convertView.tag = viewHolder}
        else{
            viewHolder = convertView.tag as ViewHolder
        }
        viewHolder.name.text = playerList[position].user_name
        viewHolder.score.text = playerList[position].score


        return convertView!!
    }
}