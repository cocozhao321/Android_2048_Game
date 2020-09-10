package com.example.finalgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_community.*

class SecondFragment : Fragment() {
    var posts = arrayListOf<post>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.activity_community, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val dataReference = FirebaseDatabase.getInstance().getReference("images");
        val query = dataReference.orderByValue()
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                posts.clear()
                for (postSnapshot in dataSnapshot.children) {
                    val key = postSnapshot.getKey()!!
                    val map : Map<String, String> = postSnapshot.value as Map<String, String>
                    val newScore : String = map.getOrDefault("score","no date")
                    val newUser : String = map.getOrDefault("user","no date")
                    val newDescription : String = map.getOrDefault("description","no description")
                    val newDate : String = map.getOrDefault("date","no photo")
                    posts.add(post(newScore, newUser, newDescription,newDate,key))
                }
                postView.adapter = RecyclerViewAdapter(getActivity(), posts){
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }

        })

        postView.layoutManager = LinearLayoutManager(getActivity())

        postView.adapter = RecyclerViewAdapter(getActivity(), posts){
        }

    }
}
