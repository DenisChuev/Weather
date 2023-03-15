package dc.weather.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dc.weather.R
import dc.weather.databinding.ItemMainDailyBinding
import dc.weather.business.model.DailyWeatherModel

class MainDailyListAdapter : BaseAdapter<DailyWeatherModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_main_daily, parent, false)
        return DailyViewHolder(view)
    }

    inner class DailyViewHolder(view: View) : BaseViewHolder(view) {
        private val binding = ItemMainDailyBinding.bind(view)

        override fun bindView(position: Int) {
            binding.itemDailyDate.text = "25 Saturday"
            binding.itemPopRate.text = "25%"
            binding.itemDailyMinTemp.text = "10\u00B0"
            binding.itemDailyMaxTemp.text = "20\u00B0"
            binding.itemDailyIcon.setImageResource(R.drawable.ic_sunrise)
        }

    }
}