package net.thesimson.petshopboy

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.widget.ImageView
import okhttp3.*
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap

import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


object PawCache {
    lateinit var client: OkHttpClient

    val cache = ConcurrentHashMap<String, Bitmap>()

    suspend fun getResponse(url : String) = suspendCoroutine<Response> {
        val request = Request.Builder().url(url).build()
        PawCache.client.newCall(request)
            .enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    if (!response.isSuccessful) it.resume(response)
                }

                override fun onFailure(call: Call, e: IOException) {
                    it.resumeWithException(e)
                }
            })
    }

    suspend fun requestContent(url: String) =
        getResponse(url)


    // Request image, and load it into ImageView
    // Errors are ignored here- a new attempt to fetch an uncached image
    // will be made next time UI needs it...

    suspend fun requestImage(imageUrl: String, dest: ImageView) {
        val uiThreadHandler=Handler()
        if( cache.containsKey(imageUrl)){
            dest.setImageBitmap(cache[imageUrl])
            return
        }
        try {
            val response = getResponse(imageUrl)
            val bm = BitmapFactory.decodeStream(response.body!!.byteStream())

            uiThreadHandler.post(Runnable {
                cache[imageUrl]=bm
                dest.setImageBitmap(bm)
            })
        }catch (e:Exception){
            println(e.message)
        }
    }

}
