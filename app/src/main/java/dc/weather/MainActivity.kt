package dc.weather

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dc.weather.view.adapters.MainDailyListAdapter
import dc.weather.view.adapters.MainHourlyListAdapter
import kotlinx.android.synthetic.main.activity_main.*

const val TAG = "MainActivity"
const val GEO_LOCATION_REQUEST_SUCCESS = 1

class MainActivity : AppCompatActivity() {
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

        checkPermision()
        geoService.requestLocationUpdates(locationRequest, geoCallback, null)
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.d(TAG, "onRequestPermissionsResult $requestCode")
    }

    private fun checkPermision() {
        if (ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            MaterialAlertDialogBuilder(this)
                .setTitle("Требуется разрешение для geo")
                .setMessage("Пожалуйста, разрешите геоданные для работы приложения")
                .setPositiveButton("ok") { _, _ ->
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                        GEO_LOCATION_REQUEST_SUCCESS
                    )
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),
                        GEO_LOCATION_REQUEST_SUCCESS
                    )
                }
                .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
                .create()
                .show()

        }
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
                Log.d(TAG, "Location ${location.latitude}, ${location.longitude}")
            }

        }
    }
}