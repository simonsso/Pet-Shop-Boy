package net.thesimson.petshopboy

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.widget.ImageView
import androidx.constraintlayout.solver.Cache
import okhttp3.*
import java.io.File
import java.io.IOException
import java.net.URL
import java.util.concurrent.ConcurrentHashMap
import kotlin.concurrent.thread


object PawCache {
    lateinit var client: OkHttpClient

    val cache = ConcurrentHashMap<String, Bitmap>()

    fun isCached(url:String):Boolean{
        return cache.containsKey(url)
    }

    fun get(url: String): Bitmap? {
        return cache[url]
    }

    fun set(url: String, img: Bitmap) {
        cache[url] = img
    }

    fun request(url: String, dest: ImageView) {
        val uiThreadHandler=Handler()
        thread {
            try {
                val request = Request.Builder().url(url).build()
                client.newCall(request)
                    .enqueue (object :Callback  {

                        override fun onResponse(call: Call, response: Response) {
                            if (!response.isSuccessful) return
                            val bm = BitmapFactory.decodeStream(response.body!!.byteStream())
                            uiThreadHandler.post(Runnable {
                                dest.setImageBitmap(bm)
                                cache[url] = bm
                            })
                        }

                        override fun onFailure(call: Call, e: IOException) {
                            // Do nothing on failure
                        }
                    })
            }catch (e:Exception){
                println(e.message)
            }
        }
    }
}
