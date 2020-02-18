package net.thesimson.petshopboy

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.util.Log
import android.widget.ImageView
import okhttp3.*
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap

import kotlin.concurrent.thread


object PawCache {
    lateinit var client: OkHttpClient

    val cache = ConcurrentHashMap<String, Bitmap>()

    fun requestContent(url: String, onDataFetched:(String)->Unit ){
        thread {
            try {
                val request = Request.Builder().url(url).build()
                client.newCall(request)
                    .enqueue (object :Callback  {
                        override fun onResponse(call: Call, response: Response) {
                            if (!response.isSuccessful) return
                            onDataFetched(response.body!!.string())
                        }
                        override fun onFailure(call: Call, e: IOException) {
                            // Do nothing on failure
                        }
                    })
            }catch (e:Exception){
                Log.w( this::class.java!!.getName(),e.message)
            }
        }
    }

    // Request image, and load it into ImageView
    // Errors are ignored here- a new attempt to fetch an uncached image
    // will be made next time UI needs it...

    fun requestImage(imageUrl: String, dest: ImageView) {
        val uiThreadHandler=Handler()
        if( cache.containsKey(imageUrl)){
            dest.setImageBitmap(cache[imageUrl])
            return
        }
        thread {
            try {
                val request = Request.Builder().url(imageUrl).build()
                client.newCall(request)
                    .enqueue (object :Callback  {
                        override fun onResponse(call: Call, response: Response) {
                            if (!response.isSuccessful) return
                            val bm = BitmapFactory.decodeStream(response.body!!.byteStream())
                            uiThreadHandler.post(Runnable {
                                cache[imageUrl]=bm
                                dest.setImageBitmap(bm)
                            })
                        }
                        override fun onFailure(call: Call, e: IOException) {
                            Log.d( this::class.java!!.getName(),e.message)
                        }
                    })
            }catch (e:Exception){
                Log.w( this::class.java!!.getName(),e.message)
            }
        }
    }
}
