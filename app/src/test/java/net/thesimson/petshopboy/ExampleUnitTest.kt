package net.thesimson.petshopboy

import org.junit.Test

import org.junit.Assert.*
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun verifyShopHours() {

        ShopHours.parseBuisinessHours("M-F 9:00 - 18:00")
        assert(ShopHours.openAt.get(Calendar.HOUR_OF_DAY) == 9 )
        assert(ShopHours.openAt.get(Calendar.MINUTE) == 0 )

        assert(ShopHours.closeAt.get(Calendar.HOUR_OF_DAY) == 18 )
        assert(ShopHours.closeAt.get(Calendar.MINUTE) == 0 )

        ShopHours.parseBuisinessHours("M-F 8:01 - 23:45")
        assert(ShopHours.openAt.get(Calendar.HOUR_OF_DAY) == 8 )
        assert(ShopHours.openAt.get(Calendar.MINUTE) == 1 )

        assert(ShopHours.closeAt.get(Calendar.HOUR_OF_DAY) == 23 )
        assert(ShopHours.closeAt.get(Calendar.MINUTE) == 45 )

        ShopHours.parseBuisinessHours("M-F 10 - 19")
        assert(ShopHours.openAt.get(Calendar.HOUR_OF_DAY) == 10 )
        assert(ShopHours.openAt.get(Calendar.MINUTE) == 0 )

        assert(ShopHours.closeAt.get(Calendar.HOUR_OF_DAY) == 19 )
        assert(ShopHours.closeAt.get(Calendar.MINUTE) == 0 )


        val now = Calendar.getInstance()
        now.set(2020,2,3,22,22,10)
        assert(now.get(Calendar.YEAR) == 2020)

        assertEquals(ShopHours.isShopIsOpen(now), false)
        now.set(2020,2,3,10,22,10)
        assertEquals(ShopHours.isShopIsOpen(now), true)
        now.set(2020,2,3,18,59,59)
        assertEquals(ShopHours.isShopIsOpen(now), true)

    }
}
