package com.example.finalgame

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment.*

class ResultActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment)
        val fragmentAdapter = MyPagerAdapter(supportFragmentManager)
        pager.adapter = fragmentAdapter
        tabs_main.setupWithViewPager(pager)

    }
    //Menu Functions
    override fun onCreateOptionsMenu(menu: Menu):Boolean{
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem):Boolean{
        if(item.itemId == R.id.new_game){
            showDialogue()
        }
        else if(item.itemId==R.id.leader_board){
            Toast.makeText(this, "You are at the result page", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    fun showDialogue(){
        val dialogTitle="CONFIRM"
        val dialogMessage = "Start/Restart the game?"
        val dialogBuilder= AlertDialog.Builder(this)
        dialogBuilder
            .setTitle(dialogTitle)
            .setMessage(dialogMessage)
            .setPositiveButton("YES"){dialog, which ->
                Toast.makeText(this, "Restart the game", Toast.LENGTH_LONG).show()
                val game_intent = Intent(this, GameActivity::class.java)
                startActivity(game_intent)
            }
            .setNegativeButton("No"){dialog, which ->
                Toast.makeText(this, "Stay", Toast.LENGTH_SHORT).show()
            }
            .create()
            .show()
    }

}