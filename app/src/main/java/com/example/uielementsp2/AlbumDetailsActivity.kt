package com.example.uielementsp2

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView

class AlbumDetailsActivity : AppCompatActivity() {

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_details)

        val viewName = findViewById<TextView>(R.id.albumTitleTextView)
        val viewImage = findViewById<ImageView>(R.id.albumImageView)

        var albumSongs :Array<String> =arrayOf()
        val position = intent.extras!!.getString("position")

        if (position.equals("Moira Dela Torre")){
            viewName.text = position
            viewImage.setImageResource(R.drawable.moira)
            albumSongs = arrayOf("Paubaya","Ang Iwasan","Patawad","Handa, Awit","Pahinga")
        }
        else if (position.equals("Ben&Ben")){
            viewName.text = position
            viewImage.setImageResource(R.drawable.benben)
            albumSongs = arrayOf("Doors","Di Ka Sayang","Lifetime","Nakikinig Ka Ba Sa Akin","Forever Beautiful")
        }
        else if (position.equals("Reese Lansangan")){
            viewName.text = position
            viewImage.setImageResource(R.drawable.reese)
            albumSongs = arrayOf("When It Happens","Awit Ng Bagong Taon","Ghosting","The Encyclopedia Salesman","Mall Rats")
        }
        var adapter = ArrayAdapter(this , android.R.layout.simple_list_item_1 , albumSongs)
        var albumDetailListView = findViewById<ListView>(R.id.albumDetailListView)
        albumDetailListView.adapter = adapter
    }
}