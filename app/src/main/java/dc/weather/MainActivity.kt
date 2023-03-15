package dc.weather

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import dc.weather.business.model.DailyWeatherModel
import dc.weather.business.model.HourlyWeatherModel
import dc.weather.business.model.WeatherCurrentModel
import dc.weather.presenters.MainPresenter
import dc.weather.view.*
import dc.weather.view.adapters.MainDailyListAdapter
import dc.weather.view.adapters.MainHourlyListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

const val TAG = "MainActivity"

class MainActivity : MvpAppCompatActivity(), MainView {
    private val mainPresenter by moxyPresenter { MainPresenter() }

    private val geoService by lazy { LocationServices.getFusedLocationProviderClient(this) }
    private val locationRequest by lazy { initLocationRequest() }

    private lateinit var mLocation: Location

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_hourly_list.apply {
            adapter = MainHourlyListAdapter()
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
        main_daily_list.apply {
            adapter = MainDailyListAdapter()
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }
        mainPresenter.enable()
        geoService.requestLocationUpdates(locationRequest, geoCallback, null)
    }

    // location logic
    private fun initLocationRequest(): LocationRequest {
        return LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000).build()
    }

    private val geoCallback = object : LocationCallback() {
        override fun onLocationResult(geo: LocationResult) {
            Log.d(TAG, "onLocationResult ${geo.locations.size}")

            for (location in geo.locations) {
                mLocation = location
                mainPresenter.refresh(location.latitude.toString(), location.longitude.toString())
                Log.d(TAG, "Location ${location.latitude}, ${location.longitude}")
            }

        }
    }

    // moxy logic
    override fun displayLocation(data: String) {
        main_city_name.text = data
    }

    override fun displayCurrentData(data: WeatherCurrentModel) {
        data.apply {
            main_city_name.text = name
            cloud_view.setImageResource(weather[0].icon.provideIcon())
            main_date_tv.text = dt.toDateFormatOf(DAY_FULL_MONTH_NAME)
            main_weather_tv.text = weather[0].description
            main_weather_degree_tv.text = main.temp.toDegree()
            main_min_degree_tv.text = main.temp_min.toDegree()
            main_max_degree_tv.text = main.temp_max.toDegree()
            main_therm_tv.text = buildString {
                append(main.pressure.toString())
                append(" hPa")
            }
            main_humidity_tv.text = buildString {
                append(main.humidity.toString())
                append(" %")
            }
            main_wind_tv.text = buildString {
                append(wind.speed.toString())
                append(" m/s")
            }
            main_sunrise_tv.text = sys.sunrise.toDateFormatOf(HOUR_DOUBLE_MINUTE)
            main_sunset_tv.text = sys.sunset.toDateFormatOf(HOUR_DOUBLE_MINUTE)
        }
    }

    override fun displayHourlyData(data: List<HourlyWeatherModel>) {
        (main_hourly_list.adapter as MainHourlyListAdapter).updateData(data)
    }

    override fun displayDailyData(data: List<DailyWeatherModel>) {
        (main_hourly_list.adapter as MainDailyListAdapter).updateData(data)
    }

    override fun displayError(error: Throwable) {
    }

    override fun setLoading(flag: Boolean) {
    }
}