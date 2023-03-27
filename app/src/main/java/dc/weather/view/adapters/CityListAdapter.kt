package dc.weather.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.button.MaterialButton
import dc.weather.R
import dc.weather.business.model.GeoCodeModel
import dc.weather.databinding.ItemCityListBinding

class CityListAdapter : BaseAdapter<GeoCodeModel>() {
    lateinit var clickListener: SearchItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return CitySearchViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_city_list, parent, false)
        )
    }

    interface SearchItemClickListener {
        fun addToFavorite(item: GeoCodeModel)
        fun removeFromFavorite(item: GeoCodeModel)
        fun showWeatherIn(item: GeoCodeModel)
    }

    inner class CitySearchViewHolder(view: View) : BaseViewHolder(view) {
        private val binding = ItemCityListBinding.bind(view)

        override fun bindView(position: Int) {
            binding.location.setOnClickListener {
                clickListener.showWeatherIn(mData[position])
            }
            binding.favorite.setOnClickListener {
                val item = mData[position]
                when ((it as MaterialButton).isChecked) {
                    true -> {
                        item.isFavorite = true
                        clickListener.addToFavorite(item)
                    }
                    false -> {
                        item.isFavorite = false
                        clickListener.removeFromFavorite(item)
                    }
                }
            }

            mData[position].apply {
                binding.state.text = if (!state.isNullOrEmpty()) ", $state" else ""
                binding.searchCity.text = local_names.ru
                binding.searchCountry.text = country
                binding.favorite.isChecked = isFavorite
            }
        }
    }
}
