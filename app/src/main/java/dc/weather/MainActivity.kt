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
import dc.weather.view.MainView
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

        updateUI()
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

    private fun updateUI() {
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