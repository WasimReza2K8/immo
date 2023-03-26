package com.example.wasim

import com.example.core.navigation.Launcher
import com.wasim.realestate.presentation.RealEstateFeature
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeatureProvider @Inject constructor(
    val realEstateFeature: RealEstateFeature,
) {
    val features: List<Launcher> = listOf(realEstateFeature)
}
