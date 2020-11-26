package com.example.uielementsp2

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi

class AlbumDetailsActivity : AppCompatActivity() {
    lateinit var adapter : ArrayAdapter<String>
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "i.apps.notifications"
    private val description = "Test notification"
    @SuppressLint("WrongViewCast")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_details)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val albumTitleTextView = findViewById<TextView>(R.id.albumTitleTextView)
        val albumImageView= findViewById<ImageView>(R.id.albumImageView)

        val position: String? = intent.extras!!.getString("position")
        if (position.equals("Moira Dela Torre")) {
            albumTitleTextView.text = position
            albumImageView.setImageResource(R.drawable.moira)
            selectedSong = arrayListOf("Paubaya" , "Ang Iwasan" , "Patawad" , "Handa, Awit" , "Pahinga" ,
                "Paubaya" , "Ang Iwasan" , "Patawad" , "Handa, Awit" , "Pahinga" ,
                "Patawad, Paalam" , "Ikaw Pa Rin" , "Beautiful" , "Mabagal" , "Kahit Kunwari Man Lang")
        }
        else if (position.equals("Ben&Ben")){
            albumTitleTextView.text = position
            albumImageView.setImageResource(R.drawable.benben)
            selectedSong = arrayListOf("Doors" , "Di Ka Sayang" , "Lifetime" , "Nakikinig Ka Ba Sa Akin" , "Forever Beautiful")
        }
        else if (position.equals("Reese Lansangan")){
            albumTitleTextView.text = position
            albumImageView.setImageResource(R.drawable.reese)
            selectedSong = arrayListOf("When It Happens" , "Awit Ng Bagong Taon" , "Ghosting" , "The Encyclopedia Salesman" , "Mall Rats")
        }
        adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, selectedSong)
        var albumDetailListView = findViewById<ListView>(R.id.albumDetailListView)
        albumDetailListView.adapter = adapter

        registerForContextMenu(albumDetailListView)
    }
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.remove_option, menu)
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.remove_song -> {
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setMessage("Do you really want to delete this?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", { _, _ ->


                        val toast: Toast = Toast.makeText(
                            applicationContext,
                            "Song removed from album.",
                            Toast.LENGTH_SHORT
                        )
                        toast.show()

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            notificationChannel = NotificationChannel(
                                channelId, description, NotificationManager.IMPORTANCE_HIGH
                            )
                            notificationChannel.enableLights(true)
                            notificationChannel.lightColor = Color.BLUE
                            notificationChannel.enableVibration(true)
                            notificationManager.createNotificationChannel(notificationChannel)

                            builder = Notification.Builder(this, channelId)
                                .setContentTitle("Song Removed")
                                .setContentText("You deleted a song from the album.")
                                .setSmallIcon(R.drawable.ic_launcher_background)

                        } else {
                            builder = Notification.Builder(this)
                                .setContentTitle("Song Removed")
                                .setContentText("You deleted a song from the album.")
                                .setSmallIcon(R.drawable.ic_launcher_background)
                        }
                        notificationManager.notify(1234, builder.build())
                        adapter.notifyDataSetChanged() // REMOVE SONG AFTER CLICKING YES
                    }).setNegativeButton("No", { dialog, _ ->
                        dialog.cancel()
                    })

                val alert = dialogBuilder.create()
                alert.setTitle("Notification Manager")
                alert.show()

                val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
                selectedSong.removeAt(info.position)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}