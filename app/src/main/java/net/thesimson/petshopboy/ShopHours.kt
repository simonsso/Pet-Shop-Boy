package net.thesimson.petshopboy

import java.util.*

object ShopHours {
    // TODO support for weekends and work days
    var openAt:Int = 0
    var closeAt:Int = 0

    fun parseBuisinessHours(s:String):Unit{

        val pattern = Regex("""(?<days>[\w-]+)\s+(?<openh>\d+)(:(?<openm>\d+))?\s*-\s*(?<closeh>\d+)(:(?<closem>\d+))?""")

        val mat:MatchResult? =pattern.matchEntire(s)
        if (mat != null ){
            val groups = mat.groups
            val openHour:Int = groups["openh"]?.value?.toIntOrNull()?:0
            val openMin:Int = groups["openm"]?.value?.toIntOrNull()?:0
            val closeHour:Int = groups["closeh"]?.value?.toIntOrNull()?:0
            val closeMin:Int = groups["closem"]?.value?.toIntOrNull()?:0

            openAt = 60 * openHour + openMin
            closeAt = 60 * closeHour + closeMin
        }
        //TODO: ELSE... parse opening hours failed, this should be handled.
    }

    fun isShopIsOpen(time:Calendar):Boolean{
        return  time.get(Calendar.HOUR_OF_DAY) * 60 + time.get(Calendar.MINUTE) in openAt..closeAt
    }
}