package net.thesimson.petshopboy

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.UiThread
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import java.net.URL
import java.util.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    @UiThread
    fun displayOpenHours() {
        // Create an alertbox dialog
        val dialogBuilder = AlertDialog.Builder(this)

        val storeOpenGreetingMessage:String = if( ShopHours.isShopOpen(Calendar.getInstance()) ){
                resources.getString(R.string.thanksshopopen)
            }else {
                resources.getString(R.string.thanksshopclosed)
        }
        dialogBuilder.setMessage(storeOpenGreetingMessage)
            .setCancelable(false)
            .setPositiveButton(resources.getString(R.string.ok), DialogInterface.OnClickListener { dialog, _id ->
                dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle(resources.getString(R.string.alerttitle))
        alert.show()
    }

    @UiThread
    fun updateCallButtonsVisability(){
        chat_button.visibility = if (ShopHours.chatEnabled) {
            View.VISIBLE
        } else {
            View.GONE
        }
        call_button.visibility = if (ShopHours.callEnabled) {
            View.VISIBLE
        } else {
            View.GONE
        }
        workHours.text = if (ShopHours.humanReadableSign != "") {
             resources.getString(R.string.officehours) + "  " + ShopHours.humanReadableSign
        } else {
            resources.getString(R.string.hours_not_loaded_from_config)
        }
    }
    @UiThread
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        call_button.setOnClickListener{displayOpenHours()}
        chat_button.setOnClickListener{displayOpenHours()}
        updateCallButtonsVisability()

        val t1 = thread{
            // Spin off in network thread
            val config_url = URL("https://simonsso.github.io/Pet-Shop-Boy/config.json")

            try {
                val config_json_string = config_url.readText()
                // NB file is known to be malformated json finding the outermost JSONObject
                val config = JSONObject(
                    config_json_string.substring(
                        config_json_string.indexOf("{"),
                        config_json_string.lastIndexOf("}") + 1
                    )
                )
                // Config loaded OK, update UI!
                this@MainActivity.runOnUiThread {
                    ShopHours.parseBuisinessHours(config.optString("workHours"))
                    ShopHours.chatEnabled = config.optBoolean("isChatEnabled")
                    ShopHours.callEnabled = config.optBoolean("isCallEnabled")

                    updateCallButtonsVisability()
                }
            }catch (e:Exception){
                // Network error or other rerun when view is updated on phone turn or link clicked.
            }

        }

        val pets_json_string = application.assets.open("pets.json").bufferedReader().use{
            it.readText()
        }
        // NB file is known to be malformated json finding the outermost JSONObject
        val pets = JSONArray( pets_json_string.substring(pets_json_string.indexOf("["), pets_json_string.lastIndexOf("]") + 1))



        val adapter = SensorListRecyclerViewAdapter(this, pets, { x-> {}  })

        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler_view.adapter = adapter
        adapter.notifyDataSetChanged()
//        val pets_string = application.assets.open("pets.json").bufferedReader().use{
//            it.readText()
//        }

    }
}
