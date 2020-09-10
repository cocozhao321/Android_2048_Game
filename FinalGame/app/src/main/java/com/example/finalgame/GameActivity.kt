package com.example.finalgame


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {
    private lateinit var gameView: GameView
    var score = 0
        set(score) {
            field = score
            (findViewById<TextView>(R.id.score)).text = "$score"
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        gameActivity = this

    }
    //Menu Functions
    override fun onCreateOptionsMenu(menu: Menu):Boolean{
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem):Boolean{
        if(item.itemId == R.id.new_game){
            showDialogue(1)
        }
        else if(item.itemId==R.id.leader_board){
            showDialogue(2)
        }
        return true
    }

    fun showDialogue(choice: Int){
        val dialogTitle="CONFIRM"
        var dialogMessage = ""
        if(choice==1) {
            dialogMessage = "Start/Restart the game?"
        }
        if(choice==2) {
            dialogMessage = "You cannot come back to current state. Click 'yes' to confirm your action"
        }
        val dialogBuilder= AlertDialog.Builder(this)
        dialogBuilder
            .setTitle(dialogTitle)
            .setMessage(dialogMessage)
            .setPositiveButton("YES"){dialog, which ->
                if (choice==1) {
                    Toast.makeText(this, "New Game", Toast.LENGTH_LONG).show()
                    gameView.startGame()
                }
                else{
                    Toast.makeText(this,"Records/Leaderboard", Toast.LENGTH_LONG).show()
                    val lead_intent = Intent(this, ResultActivity::class.java)
                    startActivity(lead_intent)
                }
            }
            .setNegativeButton("No"){dialog, which ->
                Toast.makeText(this, "Stay", Toast.LENGTH_SHORT).show()
            }
            .create()
            .show()
    }
    companion object {
        var gameActivity: GameActivity? = null
            private set
    }

}