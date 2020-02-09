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
        assert(ShopHours.openAt == 9*60 )
        assert(ShopHours.closeAt == 18*60 )

        ShopHours.parseBuisinessHours("M-F 8:01 - 23:45")
        assert(ShopHours.openAt == 8*60+1 )
        assert(ShopHours.closeAt == 23*60+45 )

        ShopHours.parseBuisinessHours("M-F 10 - 19")
        assert(ShopHours.openAt == 10*60 )
        assert(ShopHours.closeAt == 19*60 )


        val now = Calendar.getInstance()
        now.set(2020,2,3,22,22,10)
        assert(now.get(Calendar.YEAR) == 2020)

        assertEquals(ShopHours.isShopOpen(now), false)
        now.set(2020,2,3,10,22,10)
        assertEquals(ShopHours.isShopOpen(now), true)
        now.set(2020,2,3,18,59,59)
        assertEquals(ShopHours.isShopOpen(now), true)

    }
}
