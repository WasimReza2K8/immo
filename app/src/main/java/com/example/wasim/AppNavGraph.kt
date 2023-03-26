package com.example.wasim

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost

@Composable
fun AppNavGraph(
    navController: NavHostController,
    featureProvider: FeatureProvider,
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = featureProvider.realEstateFeature.route()
    ) {
        featureProvider.features.forEach { feature ->
            feature.registerGraph(this)
        }
    }
}
