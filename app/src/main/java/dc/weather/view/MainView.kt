package dc.weather.view

import dc.weather.business.model.DailyWeatherModel
import dc.weather.business.model.HourlyWeatherModel
import dc.weather.business.model.WeatherCurrentModel
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface MainView : MvpView {
    @AddToEndSingle
    fun displayLocation(data: String)

    @AddToEndSingle
    fun displayCurrentData(data: WeatherCurrentModel)

    @AddToEndSingle
    fun displayHourlyData(data: List<HourlyWeatherModel>)

    @AddToEndSingle
    fun displayDailyData(data: List<DailyWeatherModel>)

    @AddToEndSingle
    fun displayError(error: Throwable)

    @AddToEndSingle
    fun setLoading(flag: Boolean)
}
