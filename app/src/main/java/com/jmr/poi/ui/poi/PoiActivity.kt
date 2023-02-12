package com.jmr.poi.ui.poi

import android.os.Bundle
import com.jmr.poi.databinding.ActivityPoiBinding
import com.jmr.poi.ui.base.BaseActivity
import com.jmr.poi.ui.poi.list.PoiListFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PoiActivity : BaseActivity<ActivityPoiBinding>() {

    override fun createBinding(): ActivityPoiBinding =
        ActivityPoiBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)
        updateToolbar(true)

        binding.toolbar.setNavigationOnClickListener {
            supportFragmentManager.popBackStack()
        }

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(com.jmr.poi.R.id.fragment_container, PoiListFragment.newInstance())
                .commit()
        }
    }

    fun updateToolbar(isMainScreen: Boolean) {
        binding.toolbar.title =
            if (isMainScreen) getString(com.jmr.poi.R.string.poi_list_toolbar_title)
            else getString(com.jmr.poi.R.string.poi_detail_toolbar_title)

        binding.toolbar.subtitle =
            if (isMainScreen) getString(com.jmr.poi.R.string.poi_list_toolbar_subtitle)
            else ""

        supportActionBar?.setDisplayHomeAsUpEnabled(!isMainScreen)
        supportActionBar?.setDisplayShowHomeEnabled(!isMainScreen)
    }

}