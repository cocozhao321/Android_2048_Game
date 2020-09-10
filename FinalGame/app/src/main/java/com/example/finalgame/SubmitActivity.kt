package com.example.finalgame

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_submit.*
import android.graphics.Bitmap
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.createBitmap
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_record.*
import java.text.SimpleDateFormat
import java.util.*


class SubmitActivity : AppCompatActivity() {
    val MY_PERMISSIONS_REQUEST_CAMERA = 1
    val REQUEST_IMAGE_CAPTURE = 1
    companion object{
        var final_score = ""
        var name = ""
        var id = ""
        var imageBitmap = createBitmap(60,60)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit)
        final_score = GameActivity.gameActivity!!.score.toString()
        game_over_text.text=getString(R.string.game_over_message,final_score)
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=
            PackageManager.PERMISSION_GRANTED){
            //we don't have permission yet
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.INTERNET),
                MY_PERMISSIONS_REQUEST_CAMERA)
        } else{
            //we have permission
        }
        photo.setOnClickListener{view->
            dispatchTakePictureIntent()
        }
        leaderboardBT.setOnClickListener {
            val intent = Intent(this, ResultActivity::class.java)
            name = user_name.text.toString()
            id = userid.text.toString()
            sendToServer()
            //intent.putExtra("Username", name)
            //intent.putExtra("scores",final_score)
            startActivity(intent)
        }

    }
    override fun onRequestPermissionsResult(requestCode:Int, permissions: Array<String>, grantResults:IntArray){
        when (requestCode){
            MY_PERMISSIONS_REQUEST_CAMERA ->{
                if((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                    Toast.makeText(this, "Great!", Toast.LENGTH_SHORT).show()
                } else{
                    Toast.makeText(this, "Sorry, no camera access", Toast.LENGTH_SHORT).show()
                }
                return
            }
            else ->{}
        }
    }
    private fun dispatchTakePictureIntent(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)

            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            super.onActivityResult(requestCode, resultCode, data)
            imageBitmap = data?.extras?.get("data") as Bitmap
            photo.setImageBitmap(imageBitmap)
            //record_photo.setImageBitmap(imageBitmap)
        }
    }
    fun sendToServer(){
        //val storageRef = FirebaseStorage.getInstance().reference.child("images")
        val timeStamp: String = SimpleDateFormat("MM-dd-yyyy").format(Date())
        writeNewImageInfoToDB(
            final_score,
            userid.text.toString(),
            user_name.text.toString(),
            timeStamp
        )
        /*
            val fireRef = storageRef!!.child(timeStamp + ".jpg")
            fireRef
                .putBytes(data)
                .addOnSuccessListener {
                    fireRef.downloadUrl.addOnSuccessListener { taskUrl ->

                        writeNewImageInfoToDB(
                            userid.text.toString(),
                            user_name.text.toString(),
                            timeStamp,
                            taskUrl.toString()
                        )
                    }
                }
                .addOnFailureListener { exception -> }
                .addOnProgressListener { taskSnapshot -> }
                .addOnPausedListener { taskSnapshot -> }

         */

    }
    val dataReference = FirebaseDatabase.getInstance().getReference("images")
    private fun writeNewImageInfoToDB(score: String, user: String, description: String, date: String){
        val info = post(score, user, description,date)
        val key = dataReference.push().key
        dataReference.child(key!!).setValue(info)
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
                    Toast.makeText(this,"Leaderboard", Toast.LENGTH_LONG).show()
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

}