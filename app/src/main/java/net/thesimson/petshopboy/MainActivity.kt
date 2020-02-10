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
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    @UiThread
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // One time disposable buttons ;-)
        chat_button.setOnClickListener { chat_button.visibility = View.GONE }
//        call_button.setOnClickListener { call_button.visibility = View.GONE }

        // when button is clicked, show the alert
        call_button.setOnClickListener {
            // build alert dialog
            val dialogBuilder = AlertDialog.Builder(this)

            // set message of alert dialog
            dialogBuilder.setMessage("Do you want to close this application ?")
                // if the dialog is cancelable
                .setCancelable(false)
                // negative button text and action
                .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                })

            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            alert.setTitle("AlertDialogExample")
            // show alert dialog
            alert.show()
        }


        val t1 = thread{
            // Spin off in network theread
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
                this@MainActivity.runOnUiThread {
                    if (config.optBoolean("isChatEnabled")) {
                        chat_button.visibility = View.VISIBLE
                    } else {
                        chat_button.visibility = View.GONE
                    }

                    if (config.optBoolean("isCallEnabled")) {
                        call_button.visibility = View.VISIBLE
                    } else {
                        call_button.visibility = View.GONE
                    }

                    if (config.has("workHours")) {
                        ShopHours.parseBuisinessHours(config.optString("workHours"))
                        workHours.text = resources.getString(R.string.officehours) +
                                " " + config.optString("workHours")
                    } else {
                        workHours.text = "Not loaded"
                    }
                }
            }catch (e:Exception){
                // Network error
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
