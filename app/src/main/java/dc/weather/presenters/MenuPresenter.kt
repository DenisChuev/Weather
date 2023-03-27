package dc.weather.presenters

import android.annotation.SuppressLint
import dc.weather.business.ApiProvider
import dc.weather.business.model.GeoCodeModel
import dc.weather.business.repos.MenuRepository
import dc.weather.business.repos.SAVED
import dc.weather.view.MenuView

class MenuPresenter : BasePresenter<MenuView>() {
    private val repo = MenuRepository(ApiProvider())

    @SuppressLint("CheckResult")
    override fun enable() {
        repo.dataEmitter.subscribe {
            viewState.setLoading(false)
            if (it.purpose == SAVED) {
                viewState.fillFavoriteList(it.data)
            } else {
                viewState.fillPredictionList(it.data)
            }
        }
    }

    fun searchFor(city: String) {
        repo.getCities(city)
    }

    fun removeLocation(data: GeoCodeModel) {
        repo.remove(data)
    }

    fun saveLocation(data: GeoCodeModel) {
        repo.add(data)
    }

    fun getFavoriteList() {
        repo.updateFavorite()
    }

}