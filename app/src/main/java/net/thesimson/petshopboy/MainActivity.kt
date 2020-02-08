package net.thesimson.petshopboy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // One time disposable buttons ;-)
        chat_button.setOnClickListener { chat_button.visibility = View.GONE }
        call_button.setOnClickListener { call_button.visibility = View.GONE }

    }
}
