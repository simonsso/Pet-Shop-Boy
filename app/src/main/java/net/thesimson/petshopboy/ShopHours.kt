package net.thesimson.petshopboy

import androidx.annotation.UiThread
import java.util.*

object ShopHours {
    // TODO support for weekends and work days
    var openAt:Int = 0
    var closeAt:Int = 0
    var humanReadableSign:String = ""
    var chatEnabled:Boolean = false
    var callEnabled:Boolean = false

    @UiThread
    fun parseBuisinessHours(s:String):Boolean{
        // Whops, Retrieving groups by name is not supported on this platform.
        // Use group numbers
        // Days: 1
        // OpenHour: 2
        // OpenMinute: 4
        // CloseHour: 5
        // CloseMinute:7
        // Live example:  https://regex101.com/r/tBRHnN/1
        val pattern = Regex("""([\w-]+)\s+(\d+)(:(\d+))?\s*-\s*(\d+)(:(\d+))?""")

        val matchResult=pattern.matchEntire(s)
        if (matchResult != null ){
            val groups = matchResult.groups
            // TODO Week day/Weekend
            val openHour:Int = groups[2]?.value?.toIntOrNull()?:0
            val openMin:Int = groups[4]?.value?.toIntOrNull()?:0
            val closeHour:Int = groups[5]?.value?.toIntOrNull()?:0
            val closeMin:Int = groups[7]?.value?.toIntOrNull()?:0

            openAt = 60 * openHour + openMin
            closeAt = 60 * closeHour + closeMin
            humanReadableSign = s
            return true
        }else{
            return false
        }
    }
    @UiThread
    fun isShopOpen(time:Calendar):Boolean{
        if(time.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
           time.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ) return false
        return  time.get(Calendar.HOUR_OF_DAY) * 60 + time.get(Calendar.MINUTE) in openAt..closeAt
    }
}
