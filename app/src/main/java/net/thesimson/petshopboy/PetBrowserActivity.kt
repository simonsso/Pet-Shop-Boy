package net.thesimson.petshopboy

import android.app.Activity
import android.os.Bundle
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_pet_browser.*

// PetBrowser is a glorified name for Wikipedia in an integrated web browser
class PetBrowserActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_browser)

        petwebview.setWebViewClient(WebViewClient())
        petwebview.loadUrl(intent.getStringExtra("loadurl"))

    }

}
