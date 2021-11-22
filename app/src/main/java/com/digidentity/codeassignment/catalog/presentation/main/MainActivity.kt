package com.digidentity.codeassignment.catalog.presentation.main


import android.view.LayoutInflater
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.digidentity.codeassignment.catalog.R
import com.digidentity.codeassignment.catalog.databinding.ActivityMainBinding
import com.digidentity.codeassignment.catalog.presentation.base.BaseBindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseBindingActivity<ActivityMainBinding>() {


    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding = { layoutInflater ->
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {

        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.fragment.id) as NavHostFragment

        val appBarConfiguration =
            AppBarConfiguration(setOf(R.id.itemsFragment, R.id.itemDetailsFragment))

        setupActionBarWithNavController(navHostFragment.navController, appBarConfiguration)


    }


}