package com.example.core.navigation

import androidx.navigation.NavOptionsBuilder
import com.example.core.navigation.NavigatorEvent.Directions
import com.example.core.navigation.NavigatorEvent.NavigateUp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator @Inject constructor() {
    private val _destinations = MutableStateFlow<NavigatorEvent?>(null)

    val destinations: StateFlow<NavigatorEvent?> = _destinations
    fun navigateUp() {
        _destinations.value = NavigateUp
    }

    fun navigate(
        route: String,
        builder: NavOptionsBuilder.() -> Unit = { launchSingleTop = true },
    ) {
        _destinations.value = Directions(route, builder)
    }
}
