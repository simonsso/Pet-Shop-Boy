package net.thesimson.petshopboy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import android.content.Intent


object PetZoo{
    var pets = ArrayList<JSONObject>()
}



class SensorListRecyclerViewAdapter(private var context: Context, private var dataList:ArrayList<JSONObject>):
    RecyclerView.Adapter<SensorListRecyclerViewAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return dataList.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.pet_row_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            val curItem = dataList[position]

            if (curItem is JSONObject) {

                val petname = curItem.optString("title")
                holder.textView.text = petname

                val image_url = curItem.optString("image_url")
                val content_url = curItem.optString("content_url")

                if (image_url!=""){
                    PawCache.requestImage(image_url,holder.icon)
                }

                holder.top.setOnClickListener {
                    val intent : Intent = Intent( context, PetBrowserActivity::class.java)
                    intent.putExtra("loadurl",content_url)
                    intent.putExtra("petname",petname)
                    context.startActivity( intent )
                }
            }
        }catch (e:JSONException){
            println(e.message)
            println(e.localizedMessage)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView:TextView =itemView.findViewById(R.id.petRVTextView)
        val icon:ImageView =itemView.findViewById(R.id.petRVImageView)
        val top:LinearLayout =itemView.findViewById(R.id.petRVLinearLayout)
    }
}
