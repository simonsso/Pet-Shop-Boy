package net.thesimson.petshopboy
import android.app.Application
import okhttp3.OkHttpClient
import  okhttp3.Cache
import okhttp3.OkHttpClient.*
import java.io.File
import java.lang.Exception
import kotlin.concurrent.thread

class PetShopBoy:Application(){
    override  fun onCreate(){
        super.onCreate()
        PawCache.client= Builder().cache(
            Cache(File(this.getCacheDir(), "http"), 100L * 1024L * 1024L)
        ).build()
    }
}
