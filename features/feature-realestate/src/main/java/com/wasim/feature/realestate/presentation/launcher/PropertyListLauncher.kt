package com.wasim.feature.realestate.presentation.launcher

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import com.example.core.ui.utils.DURATION_500_Milli_SECOND
import com.google.accompanist.navigation.animation.composable
import com.wasim.feature.realestate.presentation.view.list.PropertyListScreen
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class PropertyListLauncher @Inject constructor() {
    companion object {
        private const val ROUTE = "property_list"
    }

    fun route() = ROUTE

    fun registerGraph(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.composable(
            route = ROUTE,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(DURATION_500_Milli_SECOND)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(DURATION_500_Milli_SECOND)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(DURATION_500_Milli_SECOND)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(DURATION_500_Milli_SECOND)
                )
            }
        ) {
            PropertyListScreen()
        }
    }
}
