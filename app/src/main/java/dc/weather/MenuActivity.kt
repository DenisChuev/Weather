package dc.weather

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dc.weather.business.model.GeoCodeModel
import dc.weather.view.MenuView
import dc.weather.view.adapters.CityListAdapter
import dc.weather.view.createObservable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_menu.*
import moxy.MvpAppCompatActivity
import java.util.concurrent.TimeUnit

class MenuActivity : MvpAppCompatActivity(), MenuView {
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        initCityList(predictions)
        initCityList(favorites)

        search_input.createObservable()
            .doOnNext { setLoading(true) }
            .debounce(700, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {}
    }

    override fun setLoading(flag: Boolean) {
        loading.isActivated = true
        loading.visibility = if (flag) View.VISIBLE else View.GONE
    }

    override fun fillPredictionList(data: List<GeoCodeModel>) {
        (predictions.adapter as CityListAdapter).updateData(data)
    }

    override fun fillFavoriteList(data: List<GeoCodeModel>) {
        (favorites.adapter as CityListAdapter).updateData(data)
    }

    private val searchItemClickListener = object : CityListAdapter.SearchItemClickListener {
        override fun addToFavorite(item: GeoCodeModel) {

        }

        override fun removeFromFavorite(item: GeoCodeModel) {
        }

        override fun showWeatherIn(item: GeoCodeModel) {
            val intent = Intent(this@MenuActivity, MainActivity::class.java)
            val bundle = Bundle()
            bundle.putString("lat", item.lat.toString())
            bundle.putString("lon", item.lon.toString())
            intent.putExtra("COORDINATES", bundle)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, R.anim.fade_out)
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, R.anim.fade_out)
    }

    private fun initCityList(rv: RecyclerView) {
        val cityAdapter = CityListAdapter()
        rv.apply {
            adapter = cityAdapter
            layoutManager = object : LinearLayoutManager(this@MenuActivity, VERTICAL, false) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }
            setHasFixedSize(true)
        }
    }
}