package net.thesimson.petshopboy

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_pet_browser.*
import kotlinx.android.synthetic.main.content_pet_browser.*


class PetBrowserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_browser)
        setSupportActionBar(toolbar)


        petwebview.setWebViewClient(WebViewClient())
        petwebview.loadUrl(intent.getStringExtra("loadurl"))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
