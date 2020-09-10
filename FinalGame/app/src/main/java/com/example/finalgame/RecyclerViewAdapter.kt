package com.example.finalgame

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.post_item.view.*


class RecyclerViewAdapter(var activity: FragmentActivity?,
                          var contactList:List<post>,
                          var listener: (Int) -> Unit)  : RecyclerView.Adapter<ViewHolder>() {
    //gets the number of items in the list
    override fun getItemCount() = contactList.size

    // create viewholder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(activity).inflate(R.layout.post_item, parent, false))

    // binds the contact info to the viewholder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(contactList[position], position, listener)

    fun notifydatasetchanged(holder: ViewHolder, position: Int){
        onBindViewHolder(holder, position)
    }
    //holder.bind(contactList[position])
}
class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var scores: TextView = view.score
    var name: TextView = view.user
    var number: TextView = view.date
    var descript: TextView = view.description

    var view: View = view
    // for binding contact info to view
    fun bind(item: post, position : Int, listener: (Int) -> Unit) {

        val kkey = item.key
        try {
            scores.text = item.score
            name.text = item.user
            number.text = item.date
            descript.text = item.description
            Log.d("right","Something right happened")
        }catch(e: Exception){
            Log.d("wrong","Something wrong happened")
        }

        view.setOnClickListener{
            listener(position)
        }

    }

}