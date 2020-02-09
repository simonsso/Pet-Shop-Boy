package net.thesimson.petshopboy


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

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
                .setPositiveButton("OK", DialogInterface.OnClickListener {
                        dialog, id -> dialog.cancel()
                })

            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            alert.setTitle("AlertDialogExample")
            // show alert dialog
            alert.show()
        }



        val config_json_string = application.assets.open("config.json").bufferedReader().use{
            it.readText()
        }

        val config = JSONObject( config_json_string.substring(config_json_string.indexOf("{"), config_json_string.lastIndexOf("}") + 1))
        if ( config.optBoolean("isChatEnabled",false) ){
            chat_button.visibility = View.VISIBLE
        }else{
            chat_button.visibility = View.GONE
        }

        if ( config.optBoolean("isCallEnabled",false) ){
            call_button.visibility = View.VISIBLE
        }else{
            call_button.visibility = View.GONE
        }

        if (config.has("workHours")) {
            ShopHours.parseBuisinessHours(config.optString("workHours"))
            workHours.text=config.optString("workHours")
        }else{
            workHours.text= "Not loaded"
        }

//        val pets_string = application.assets.open("pets.json").bufferedReader().use{
//            it.readText()
//        }

    }
}
