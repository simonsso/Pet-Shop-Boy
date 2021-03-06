package net.thesimson.petshopboy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONException
import org.json.JSONObject
import android.content.Intent
import android.util.Log


object PetZoo{
    var pets = ArrayList<JSONObject>()
}

class RecomendedPetsRecyclerViewAdapter(private var context: Context, private var dataList:ArrayList<JSONObject>):
    RecyclerView.Adapter<RecomendedPetsRecyclerViewAdapter.ViewHolder>() {
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
                val petName = curItem.optString("title")
                holder.textView.text = petName

                val imageUrl = curItem.optString("image_url")
                val contentUrl = curItem.optString("content_url")

                if (imageUrl!=""){
                    PawCache.requestImage(imageUrl,holder.icon)
                }

                holder.top.setOnClickListener {
                    val intent : Intent = Intent( context, PetBrowserActivity::class.java)
                    intent.putExtra("loadurl",contentUrl)
                    intent.putExtra("petname",petName)
                    context.startActivity( intent )
                }
            }
        }catch (e:JSONException){
            Log.w( this::class.java!!.getName(),e.message)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView:TextView =itemView.findViewById(R.id.petRVTextView)
        val icon:ImageView =itemView.findViewById(R.id.petRVImageView)
        val top:LinearLayout =itemView.findViewById(R.id.petRVLinearLayout)
    }
}
