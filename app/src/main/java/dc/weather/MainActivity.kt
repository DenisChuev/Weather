package dc.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_city_name.text = "Moscow"
        main_date_tv.text = "23.02.2023"
        main_weather_tv.text = "Clear"
        main_weather_degree_tv.text = "25\u00B0"
        main_min_degree_tv.text = "19"
        main_max_degree_tv.text = "25"
        main_therm_tv.text = "1.5pHa"
        main_humidity_tv.text = "75%"
        main_wind_tv.text = "5 m/s"
        main_sunrise_tv.text = "04:30"
        main_sunset_tv.text = "22:30"
    }
}