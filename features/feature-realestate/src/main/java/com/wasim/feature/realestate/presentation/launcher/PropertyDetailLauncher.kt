package com.wasim.feature.realestate.presentation.launcher

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.core.ui.utils.DURATION_500_Milli_SECOND
import com.google.accompanist.navigation.animation.composable
import com.wasim.feature.realestate.presentation.view.detail.PropertyDetailScreen
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class PropertyDetailLauncher @Inject constructor() {
    companion object {
        const val ID = "id"
        private const val ROUTE = "property_detail"
    }

    fun route(id: Int) = "$ROUTE/$id"

    fun registerGraph(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.composable(
            route = "$ROUTE/{$ID}",
            arguments = listOf(navArgument(ID) { type = NavType.IntType }),
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
            PropertyDetailScreen()
        }
    }
}
