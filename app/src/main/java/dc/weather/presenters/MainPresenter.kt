package dc.weather.presenters

import android.annotation.SuppressLint
import android.util.Log
import dc.weather.business.ApiProvider
import dc.weather.business.repos.MainRepository
import dc.weather.view.MainView

class MainPresenter : BasePresenter<MainView>() {
    private val repo = MainRepository(ApiProvider())

    @SuppressLint("CheckResult")
    override fun enable() {
        Log.d("Presenter", "inside enable")
        repo.dataEmitter.subscribe { response ->
            Log.d("Presenter", "Enable $response")
            viewState.displayLocation(response.cityName)
            viewState.displayCurrentData(response.weatherData)
            response.error?.let { viewState.displayError(response.error) }
        }
    }

    fun refresh(lat: String, lon: String) {
        viewState.setLoading(true)
        repo.reloadData(lat, lon)
    }

}