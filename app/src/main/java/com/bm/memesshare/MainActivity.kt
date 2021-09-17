package com.bm.memesshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlin.String.Companion as String

class MainActivity : AppCompatActivity() {
    lateinit var current_url: kotlin.String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        load_meme()

    }

    private fun load_meme() {
        val progress = findViewById<ProgressBar>(R.id.prog)
        progress.visibility = View.VISIBLE
        // Instantiate the RequestQueue.
        val url = "https://meme-api.herokuapp.com/gimme"
        val memeimg = findViewById<ImageView>(R.id.memeimage)
        // Request a string response from the provided URL.

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val url = response.getString("url")
                current_url = url
                Glide.with(this).load(url).listener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress.visibility = View.GONE
                        return false
                    }

                }).into(memeimg)
            },
            {

            })

// Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun nextMeme(view: android.view.View) {
        load_meme()
    }

    fun shareMeme(view: android.view.View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(
            Intent.EXTRA_TEXT,
            "Hey, checkout this cool meme....! I got it from Reddit..!! $current_url"
        )
        val chooser = Intent.createChooser(intent, "Share This meme using..")
        startActivity(chooser)
    }
}