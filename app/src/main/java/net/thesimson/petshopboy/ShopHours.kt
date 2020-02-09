package net.thesimson.petshopboy

import java.util.*

object ShopHours {
    // TODO support for weekends and work days
    var openAt:Int = 0
    var closeAt:Int = 0

    fun parseBuisinessHours(s:String):Unit{
        // Whops, Retrieving groups by name is not supported on this platform.
        // Use group numbers
        // Days: 1
        // OpenHour: 2
        // OpenMinute: 4
        // CloseHour: 5
        // CloseMinute:7
        //  https://regex101.com/r/tBRHnN/1
        val pattern = Regex("""([\w-]+)\s+(\d+)(:(\d+))?\s*-\s*(\d+)(:(\d+))?""")

        val mat:MatchResult? =pattern.matchEntire(s)
        if (mat != null ){
            val groups = mat.groups
            val openHour:Int = groups[2]?.value?.toIntOrNull()?:0
            val openMin:Int = groups[4]?.value?.toIntOrNull()?:0
            val closeHour:Int = groups[5]?.value?.toIntOrNull()?:0
            val closeMin:Int = groups[7]?.value?.toIntOrNull()?:0

            openAt = 60 * openHour + openMin
            closeAt = 60 * closeHour + closeMin
        }
        //TODO: ELSE... parse opening hours failed, this should be handled.
    }

    fun isShopIsOpen(time:Calendar):Boolean{
        return  time.get(Calendar.HOUR_OF_DAY) * 60 + time.get(Calendar.MINUTE) in openAt..closeAt
    }
}
