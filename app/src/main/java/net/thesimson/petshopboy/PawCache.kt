package net.thesimson.petshopboy

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.widget.ImageView
import java.net.URL
import java.util.concurrent.ConcurrentHashMap
import kotlin.concurrent.thread


object PawCache {
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
                val bm = BitmapFactory.decodeStream(URL(url).openConnection().getInputStream());
                cache[url] = bm
                uiThreadHandler.post(Runnable {
                    dest.setImageBitmap(bm)
                })
            }catch (e:Exception){
                println(e.message)
            }
        }
    }
}
