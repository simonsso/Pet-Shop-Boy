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
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception
import java.util.*

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

        PawCache.requestContent( "https://simonsso.github.io/Pet-Shop-Boy/config.json") { config_json_string ->
            // NB file is known to be malformated json finding the outermost JSONObject
            val config = try {
                 JSONObject(
                    config_json_string.substring(
                        config_json_string.indexOf("{"),
                        config_json_string.lastIndexOf("}") + 1
                    )
                )
            }catch (e: JSONException) {
                // Parsing failed return an empty object
                JSONObject()
            }
            // Config loaded OK, update UI!
            this@MainActivity.runOnUiThread {
                ShopHours.parseBuisinessHours(config.optString("workHours"))
                ShopHours.chatEnabled = config.optBoolean("isChatEnabled")
                ShopHours.callEnabled = config.optBoolean("isCallEnabled")

                updateCallButtonsVisability()
            }
        }

        val adapter = SensorListRecyclerViewAdapter(this, PetZoo.pets)

        PawCache.requestContent("https://simonsso.github.io/Pet-Shop-Boy/pets.json") {pets_json_string->

                // NB file is known to be malformated json finding the outermost JSONObject
            val temp_net_pets = try {
                JSONArray(
                    pets_json_string.substring(
                        pets_json_string.indexOf("["),
                        pets_json_string.lastIndexOf("]") + 1
                    )
                )
            }catch (e:JSONException){
                JSONArray()
            }
            this@MainActivity.runOnUiThread {
                PetZoo.pets.clear()
                for (i in 0 until temp_net_pets.length()) {
                    // JSONArray is broken and will return len 11 but last item does not exist
                    // This try will cactch such an exception
                    try {
                        if (temp_net_pets.getJSONObject(i) is JSONObject) {
                            PetZoo.pets.add(temp_net_pets[i] as JSONObject)
                        }
                    }catch (e:JSONException){

                    }
                }
                adapter.notifyDataSetChanged()
            }
        }
        recycler_view.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}
