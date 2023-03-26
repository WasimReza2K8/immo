package com.wasim.feature.realestate.presentation.launcher

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.wasim.realestate.presentation.RealEstateFeature
import javax.inject.Inject

internal class RealEstateFeatureImpl @Inject constructor(
    private val propertyListLauncher: PropertyListLauncher,
    private val propertyDetailLauncher: PropertyDetailLauncher,
) : RealEstateFeature {
    companion object {
        private const val ROUTE = "real_estate"
    }

    override fun route() = ROUTE
    override fun registerGraph(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.register()
    }

    private fun NavGraphBuilder.register() {
        navigation(
            route = route(),
            startDestination = propertyListLauncher.route()
        ) {
            propertyListLauncher.registerGraph(this)
            propertyDetailLauncher.registerGraph(this)
        }
    }
}
