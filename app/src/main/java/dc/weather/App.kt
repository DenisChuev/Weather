package dc.weather

import android.app.Application
import android.content.Intent

const val APP_SETTINGS = "APP_WEATHER"
const val APP_STARTED_PREVIOUSLY = "APP_STARTED_PREVIOUSLY"

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val prefs = getSharedPreferences(APP_SETTINGS, MODE_PRIVATE)
        val flag = prefs.getBoolean(APP_STARTED_PREVIOUSLY, false)

        if (!flag) {
            prefs.edit().putBoolean(APP_STARTED_PREVIOUSLY, true).apply()
            val intent = Intent(this, InitialActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}