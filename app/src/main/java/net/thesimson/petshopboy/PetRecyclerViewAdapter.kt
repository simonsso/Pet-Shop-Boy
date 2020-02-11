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






class SensorListRecyclerViewAdapter(private var context: Context, private var dataList:JSONArray, private  var myOnClicked:(String)->Unit):
    RecyclerView.Adapter<SensorListRecyclerViewAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return dataList.length();
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
                    if ( PawCache.isCached(image_url) ){
                        holder.icon.setImageBitmap(PawCache.get(image_url))
                    }else{
                        PawCache.request(image_url,holder.icon)
                    }
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
//        if(curItem == suportedsensorsDataList.selected) {
//            holder.icon.setImageResource(R.drawable.ic_cloud_done_black_24dp)
//        }else{
//            holder.icon.setImageResource(R.drawable.ic_cloud_off_black_24dp)
//        }
//        holder.icon.visibility = View.VISIBLE

    }

//    @UiThread
//    public  fun notify_active(){
//        for (curItem in suportedsensorsDataList.dataList ) {
//
//            val icon = holderMapping[curItem]?.icon
//            if (curItem == suportedsensorsDataList.selected) {
//                icon?.setImageResource(R.drawable.ic_cloud_done_black_24dp)
//            } else {
//                icon?.setImageResource(R.drawable.ic_cloud_off_black_24dp)
//            }
//            icon?.visibility = View.VISIBLE
//        }
//    }
//    @UiThread
//    public fun notify(id:String) {
//        val view = holderMapping[id]?.top
//        if(id == suportedsensorsDataList.selected) {
//            holderMapping[id]?.icon?.setImageResource(R.drawable.ic_cloud_done_black_24dp)
//        }else {
//            holderMapping[id]?.icon?.setImageResource(R.drawable.ic_cloud_pending_orande_24dp)
//        }
//    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView:TextView =itemView.findViewById(R.id.petRVTextView)
        val icon:ImageView =itemView.findViewById(R.id.petRVImageView)
        val top:LinearLayout =itemView.findViewById(R.id.petRVLinearLayout)
    }
}
