package com.prayertracker.app

import android.content.Context
import android.webkit.JavascriptInterface
import com.prayertracker.app.room.PrayerDatabase
import com.prayertracker.app.room.PrayerEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.time.LocalDate

class WebAppInterface(
    private val context: Context
) {

    private val db =
        PrayerDatabase.getInstance(context)

    @JavascriptInterface
    fun savePrayer(json: String) {

        CoroutineScope(Dispatchers.IO).launch {

            val obj = JSONObject(json)

            val prayer = PrayerEntity(
                date = LocalDate.now().toString(),
                fajr = obj.getBoolean("fajr"),
                dhuhr = obj.getBoolean("dhuhr"),
                asr = obj.getBoolean("asr"),
                maghrib = obj.getBoolean("maghrib"),
                isha = obj.getBoolean("isha")
            )

            db.prayerDao().savePrayer(prayer)
        }
    }
}
