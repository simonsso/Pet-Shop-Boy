package net.thesimson.petshopboy

import java.util.*

object ShopHours {
    // TODO support for weekends and work days
    var openAt:Calendar = Calendar.getInstance()
    var closeAt:Calendar = Calendar.getInstance()

    fun parseBuisinessHours(s:String):Unit{

        val pattern = Regex("(?<days>[\\w-]+)\\s+(?<openh>\\d+)(:(?<openm>\\d+))?\\s*-\\s*(?<closeh>\\d+)(:(?<closem>\\d+))?")

        val mat:MatchResult? =pattern.matchEntire(s)
        if (mat != null ){
            val groups = mat.groups
            val openHour:Int = groups["openh"]?.value?.toIntOrNull()?:0
            val openMin:Int = groups["openm"]?.value?.toIntOrNull()?:0
            val closeHour:Int = groups["closeh"]?.value?.toIntOrNull()?:0
            val closeMin:Int = groups["closem"]?.value?.toIntOrNull()?:0

            openAt.set(0, 0, 0, openHour, openMin, 0)
            closeAt.set(0, 0, 0, closeHour, closeMin, 0)
        }
        //TODO: parse opening hours failed, this should be handled.
    }

    fun isShopIsOpen(time:Calendar):Boolean{
        val time = time.clone() as Calendar
        time.set(Calendar.YEAR,0)
        time.set(Calendar.MONTH,0)
        time.set(Calendar.DAY_OF_MONTH,0)
        return time in openAt..closeAt
    }
}