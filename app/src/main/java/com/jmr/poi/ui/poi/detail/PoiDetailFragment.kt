package com.jmr.poi.ui.poi.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jmr.poi.R
import com.jmr.poi.databinding.FragmentPoiDetailBinding
import com.jmr.poi.domain.base.Status
import com.jmr.poi.domain.model.poi.Poi
import com.jmr.poi.ui.base.BaseFragment
import com.jmr.poi.ui.poi.PoiActivity
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PoiDetailFragment : BaseFragment<FragmentPoiDetailBinding>(), OnMapReadyCallback {

    private val viewModel: PoiDetailViewModel by activityViewModels()

    private lateinit var mMap: GoogleMap
    private var defaultId = "1"
    private var id = defaultId
    private val zoomLevel: Float = 15.0f
    private var poiLocation = LatLng(40.416775, -3.703790)

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentPoiDetailBinding = FragmentPoiDetailBinding.inflate(inflater, container, false)

    companion object {
        const val PARAM_ID = "param-id"

        @JvmStatic
        fun newInstance(poiId: String) =
            PoiDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(PARAM_ID, poiId)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    private fun init() {
        initObservers()

        arguments?.let {
            id = it.getString(PARAM_ID, defaultId)
        }

        viewModel.requestPoi(id)

        activity?.onBackPressedDispatcher?.addCallback(
            this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    activity?.supportFragmentManager?.popBackStack()
                }
            })
    }

    private fun initObservers() {
        viewModel.getPoi()
            .observe(this, Observer {
                when (it.status) {
                    Status.LOADING -> {
                        binding?.progressBar?.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding?.progressBar?.visibility = View.GONE
                        renderData(it.data)
                    }
                    Status.EXCEPTION -> {
                        binding?.progressBar?.visibility = View.GONE
                        showToast(requireContext(), getString(R.string.exception_poi_call_toast))
                    }
                    Status.ERROR -> {
                        binding?.progressBar?.visibility = View.GONE
                        showToast(requireContext(), getString(R.string.error_poi_call_toast))
                    }
                }
            })
    }

    private fun renderData(poi: Poi?) {
        Picasso.get()
            .load(poi?.image)
            .placeholder(R.drawable.progress_animation)
            .into(binding?.ivImage)

        binding?.tvTitle?.text = poi?.title

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        if (poi?.latitude != null && poi.longitude != null)
            poiLocation = LatLng(poi.longitude, poi.latitude)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.addMarker(
            MarkerOptions().position(poiLocation).title(binding?.tvTitle?.text.toString())
        )
        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(poiLocation, zoomLevel))
        mMap.uiSettings.isScrollGesturesEnabled = true
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isZoomGesturesEnabled = true
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as PoiActivity).updateToolbar(true)
    }

}