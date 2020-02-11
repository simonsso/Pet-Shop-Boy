package net.thesimson.petshopboy

import android.graphics.BitmapFactory
import android.os.Handler
import android.widget.ImageView
import okhttp3.*
import java.io.IOException

import kotlin.concurrent.thread


object PawCache {
    lateinit var client: OkHttpClient

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
                println(e.message)
            }
        }
    }

    fun requestImage(url: String, dest: ImageView) {
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
