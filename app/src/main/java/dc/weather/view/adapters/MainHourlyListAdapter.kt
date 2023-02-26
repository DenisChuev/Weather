package dc.weather.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dc.weather.R
import dc.weather.databinding.ItemMainHourlyBinding
import dc.weather.model.HourlyWeatherModel

class MainHourlyListAdapter : BaseAdapter<HourlyWeatherModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_main_hourly, parent, false)
        return HourlyViewHolder(view)
    }

    inner class HourlyViewHolder(view: View) : BaseViewHolder(view) {
        private val binding = ItemMainHourlyBinding.bind(view)

        override fun bindView(position: Int) {
            binding.itemHourlyTime.text = "14:00"
            binding.itemHourlyTemp.text = "15\u00B0"
            binding.itemHourlyCondition.text = "100%"
            binding.itemHourlyIcon.setImageResource(R.drawable.ic_sunrise)
        }
    }
}
