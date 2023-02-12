package com.jmr.poi.ui.poi.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jmr.poi.R
import com.jmr.poi.databinding.RowPoiBinding
import com.squareup.picasso.Picasso
import com.jmr.poi.domain.model.poi.Poi


class PoiListAdapter(
    private val poiList: List<Poi>,
    private val listener: ClickItemListener
) : RecyclerView.Adapter<PoiListAdapter.PoiHolder>() {

    interface ClickItemListener {
        fun onClicked(poi: Poi)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PoiHolder {

        val itemBinding = RowPoiBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return PoiHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: PoiHolder, position: Int) {
        val poi: Poi = poiList[position]
        holder.bind(poi)
    }

    inner class PoiHolder(
        private val itemBinding: RowPoiBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(poi: Poi) {
            itemBinding.tvTitle.text = poi.title
            itemBinding.tvGeolocation.text = poi.geolocation

            Picasso.get()
                .load(poi.image)
                .placeholder(R.drawable.progress_animation)
                .into(itemBinding.ivImage)

            onClick(poi)
        }

        private fun onClick(poi: Poi) {
            itemBinding.root.setOnClickListener {
                listener.onClicked(poi)
            }
        }
    }

    override fun getItemCount(): Int = poiList.size

}
