package net.thesimson.petshopboy

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.json.JSONArray
import org.json.JSONObject

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("net.thesimson.petshopboy", appContext.packageName)


        val example_json:String = """
"settings": {
"isChatEnabled" : true,
"isCallEnabled" : true,
"workHours" : "M-F 9:00 - 19:00"
}
"""


            val example_pets = """"pets": [
        {
            "image_url": "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0b/Cat_poster_1.jpg/1200px-Cat_poster_1.jpg",
            "title": "Cat",
            "content_url": "https://en.wikipedia.org/wiki/Cat",
            "date_added": "2018-06-02T03:27:38.027Z"
        },
        {
            "image_url": "https://upload.wikimedia.org/wikipedia/commons/3/30/RabbitMilwaukee.jpg",
            "title": "Rabbit",
            "content_url": "https://en.wikipedia.org/wiki/Rabbit",
            "date_added": "2018-06-06T08:36:16.027Z"
        },
        {
            "image_url": "https://upload.wikimedia.org/wikipedia/commons/3/32/Ferret_2008.png",
            "title": "Ferret",
            "content_url": "https://en.wikipedia.org/wiki/Ferret",
            "date_added": "2018-06-23T19:14:04.027Z"
        },
        {
            "image_url": "https://upload.wikimedia.org/wikipedia/commons/e/e9/Goldfish3.jpg",
            "title": "Goldfish",
            "content_url": "https://en.wikipedia.org/wiki/Goldfish",
            "date_added": "2018-07-12T15:49:27.027Z"
        },
        {
            "image_url": "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d9/Collage_of_Nine_Dogs.jpg/1200px-Collage_of_Nine_Dogs.jpg",
            "title": "Dog",
            "content_url": "https://en.wikipedia.org/wiki/Dog",
            "date_added": "2018-07-21T11:33:29.027Z"
        },
        {
            "image_url": "https://upload.wikimedia.org/wikipedia/commons/6/6d/Blue-and-Yellow-Macaw.jpg",
            "title": "Parrot",
            "content_url": "https://en.wikipedia.org/wiki/Parrot",
            "date_added": "2018-07-26T04:20:16.027Z"
        },
        {
            "image_url": "https://upload.wikimedia.org/wikipedia/commons/9/9f/Raccoon_climbing_in_tree_-_Cropped_and_color_corrected.jpg",
            "title": "Raccoon",
            "content_url": "https://en.wikipedia.org/wiki/Raccoon",
            "date_added": "2018-07-29T20:58:39.027Z"
        },
        {
            "image_url": "https://upload.wikimedia.org/wikipedia/commons/d/d4/Igel01.jpg",
            "title": "Hedgehog",
            "content_url": "https://en.wikipedia.org/wiki/Hedgehog",
            "date_added": "2018-07-30T18:10:57.027Z"
        },
        {
            "image_url": "https://upload.wikimedia.org/wikipedia/commons/d/de/Nokota_Horses_cropped.jpg",
            "title": "Horse",
            "content_url": "https://en.wikipedia.org/wiki/Horse",
            "date_added": "2018-08-02T05:04:03.027Z"
        },
        {
            "image_url": "https://upload.wikimedia.org/wikipedia/commons/0/00/Two_adult_Guinea_Pigs_%28Cavia_porcellus%29.jpg",
            "title": "Guinea Pig",
            "content_url": "https://en.wikipedia.org/wiki/Guinea_pig",
            "date_added": "2018-08-04T10:45:29.027Z"
        },
        ]"""

        val json:String = example_json
        val jsonStr = json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1)
        val jsonObj = JSONObject(jsonStr)
        val pets = JSONArray(example_pets.substring(example_pets.indexOf("["), example_pets.lastIndexOf("]") + 1))

        ShopHours.parseBuisinessHours(jsonObj.optString("workHours"))
        assert(ShopHours.openAt == 9*60 )
        assert(ShopHours.closeAt == 18*60 )


    }

}

