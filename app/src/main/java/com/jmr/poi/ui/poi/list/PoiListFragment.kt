package com.jmr.poi.ui.poi.list

import android.R
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmr.poi.databinding.FragmentPoiListBinding
import com.jmr.poi.domain.base.Status
import com.jmr.poi.domain.model.poi.Poi
import com.jmr.poi.ui.base.BaseFragment
import com.jmr.poi.ui.poi.PoiActivity
import com.jmr.poi.ui.poi.detail.PoiDetailFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PoiListFragment : BaseFragment<FragmentPoiListBinding>(), PoiListAdapter.ClickItemListener {

    private val poiListViewModel: PoiListViewModel by activityViewModels()
    private lateinit var listAdapter: PoiListAdapter

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentPoiListBinding = FragmentPoiListBinding.inflate(inflater, container, false)

    companion object {
        @JvmStatic
        fun newInstance() = PoiListFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.fade)
        exitTransition = inflater.inflateTransition(R.transition.fade)

        initObservers()
        poiListViewModel.requestPoiList()

        binding?.bRetry?.setOnClickListener {
            poiListViewModel.requestPoiList()
        }
    }

    private fun initObservers() {
        poiListViewModel.getPoiList()
            .observe(viewLifecycleOwner) {
                when (it.status) {
                    Status.LOADING -> {
                        binding?.progressBar?.visibility = VISIBLE
                        binding?.etFilter?.visibility = GONE
                        binding?.rvList?.visibility = GONE
                        binding?.tvMessage?.visibility = GONE
                        binding?.bRetry?.visibility = GONE
                    }
                    Status.SUCCESS -> {
                        binding?.progressBar?.visibility = GONE
                        binding?.etFilter?.visibility = VISIBLE
                        binding?.rvList?.visibility = VISIBLE
                        binding?.tvMessage?.visibility = GONE
                        binding?.bRetry?.visibility = GONE
                        initAdapter(it.data!!)
                    }
                    Status.EXCEPTION -> {
                        binding?.progressBar?.visibility = GONE
                        binding?.etFilter?.visibility = GONE
                        binding?.rvList?.visibility = GONE
                        binding?.tvMessage?.visibility = VISIBLE
                        binding?.bRetry?.visibility = VISIBLE
                        binding?.tvMessage?.text =
                            getString(com.jmr.poi.R.string.exception_poi_call_message)
                        showToast(
                            requireContext(),
                            getString(com.jmr.poi.R.string.error_poi_call_toast)
                        )
                    }
                    Status.ERROR -> {
                        binding?.progressBar?.visibility = GONE
                        binding?.etFilter?.visibility = GONE
                        binding?.rvList?.visibility = GONE
                        binding?.tvMessage?.visibility = VISIBLE
                        binding?.bRetry?.visibility = VISIBLE
                        binding?.tvMessage?.text =
                            getString(com.jmr.poi.R.string.error_poi_call_message)
                        showToast(
                            requireContext(),
                            getString(com.jmr.poi.R.string.error_poi_call_toast)
                        )
                    }
                }
            }
    }

    private fun filterPoiList(poiList: List<Poi>, filterText: String): List<Poi> {
        return poiList.sortedBy { poi ->
            poi.title
        }.filter {
            it.title?.lowercase()?.contains(filterText.lowercase()) == true
        }
    }

    private fun initAdapter(poiList: List<Poi>) {
        binding?.rvList?.layoutManager = LinearLayoutManager(context)
        binding?.rvList?.addItemDecoration(DividerItemDecoration(context, 0))

        binding?.etFilter?.doAfterTextChanged { s ->
            val filterList = filterPoiList(poiList, s.toString())
            updateList(filterList)
        }

        updateList(poiList.sortedBy { poi -> poi.title })
    }

    private fun updateList(list: List<Poi>) {
        if (list.isNotEmpty()) {
            listAdapter = PoiListAdapter(list, this)
            binding?.rvList?.adapter = listAdapter
            binding?.rvList?.visibility = VISIBLE
            binding?.tvMessage?.visibility = GONE
        } else {
            binding?.rvList?.visibility = GONE
            binding?.tvMessage?.visibility = VISIBLE
            binding?.tvMessage?.text =
                getString(com.jmr.poi.R.string.poi_list_empty_with_filter_text)
        }
    }

    override fun onClicked(poi: Poi) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        val fragment = PoiDetailFragment.newInstance(poi.idPoi)

        transaction?.replace(com.jmr.poi.R.id.fragment_container, fragment)
        transaction?.addToBackStack(fragment.javaClass.simpleName)
        transaction?.commit()

        (activity as PoiActivity).updateToolbar(false)
    }

}