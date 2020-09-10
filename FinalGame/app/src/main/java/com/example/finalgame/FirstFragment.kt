package com.example.finalgame


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_record.*

class FirstFragment : Fragment() {
    companion object {
        val players: ArrayList<PlayerStat> = ArrayList<PlayerStat>()
        var first_open: Boolean = true
    }

    var new_user: String = ""
    var new_score: String = ""
    var act = getActivity()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.activity_record, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (first_open) {
            initPlayer()
            first_open = false
        }
        Log.d("check","visit here")
        if(SubmitActivity.imageBitmap != null) {
            record_photo.setImageBitmap(SubmitActivity.imageBitmap)
        }
        try {
            new_user = SubmitActivity.name
            new_score = SubmitActivity.final_score
            SubmitActivity.final_score = ""
            SubmitActivity.name = ""
            Log.d("check",new_user)
            addPlayer()
        } catch (e: Exception) {
            Log.d("except", "Something wrong")
        }
        act = getActivity()
        val playerAdapter = ListViewAdapter(act, players)
        players_list_view.adapter = playerAdapter
    }

    fun initPlayer() {
        val sharedPrefs = act?.getSharedPreferences("database", Context.MODE_PRIVATE)
        var result_name = sharedPrefs?.getString("first", null)
        var result_score = sharedPrefs?.getString("first_score", null)
        if (result_name != null && result_score != null) {
            val player1 = PlayerStat(result_name, result_score)
            players.add(player1)
        }
        result_name = sharedPrefs?.getString("second", null)
        result_score = sharedPrefs?.getString("second_score", null)
        if (result_name != null && result_score != null) {
            val player2 = PlayerStat(result_name, result_score)
            players.add(player2)
        }
        result_name = sharedPrefs?.getString("third", null)
        result_score = sharedPrefs?.getString("third_score", null)
        if (result_name != null && result_score != null) {
            val player3 = PlayerStat(result_name, result_score)
            players.add(player3)
        }
        result_name = sharedPrefs?.getString("fourth", null)
        result_score = sharedPrefs?.getString("fourth_score", null)
        if (result_name != null && result_score != null) {
            val player4 = PlayerStat(result_name, result_score)
            players.add(player4)
        }
        result_name = sharedPrefs?.getString("fifth", null)
        result_score = sharedPrefs?.getString("fifth_score", null)
        if (result_name != null && result_score != null) {
            val player5 = PlayerStat(result_name, result_score)
            players.add(player5)
        }

    }

    //add new players to the leader board
    fun addPlayer() {
        if (players.size == 0) {
            players.add(PlayerStat(new_user, new_score))
        } else {
            loop@ for (x in 0..(players.size - 1)) {
                if (new_score.toInt() >= players[x].score.toInt()) {
                    players.add(x, PlayerStat(new_user, new_score))
                    break@loop
                }
            }
        }
        if (players.size <= 5) {
            if (new_score.toInt() < players[players.size - 1].score.toInt()) {
                players.add(PlayerStat(new_user, new_score))
            }
        } else {
            players.removeAt(players.size - 1)
        }
        storeSharePreferences(true)//update the sharePreference

    }

    fun storeSharePreferences(check: Boolean) {
        val sharedPref = act?.getSharedPreferences("database", Context.MODE_PRIVATE)
        val editor = sharedPref?.edit()
        if (check) {
            if (players.size <= 5) {
                if (!first_open) {
                    //editor.clear()
                }
                editor?.putString("first", players[0].user_name)
                editor?.putString("first_score", players[0].score)
                editor?.putString("second", players[1].user_name)
                editor?.putString("second_score", players[1].score)
                editor?.putString("third", players[2].user_name)
                editor?.putString("third_score", players[2].score)
                editor?.putString("fourth", players[3].user_name)
                editor?.putString("fourth_score", players[3].score)
                editor?.putString("fifth", players[4].user_name)
                editor?.putString("fifth_score", players[4].score)
            }
        } else {
            editor?.clear()
        }
        editor?.commit()
    }
}

