package com.puzzle_agency.countriesapp.presentation.navigation

import com.ramcosta.composedestinations.spec.Direction
import javax.inject.Singleton

@Singleton
class NavigationManager {
    private var navEventListener: ((destination: NavigationEvent) -> Unit)? = null

    fun navigate(destination: NavigationEvent) {
        navEventListener?.invoke(destination)
    }

    fun setOnNavEvent(navEventListener: (destination: NavigationEvent) -> Unit) {
        this.navEventListener = navEventListener
    }
}

data class NavigationEvent(
    val direction: Direction,
    val popUpData: PopupData? = null
) {

    data class PopupData(
        val popupTo: String,
        val inclusive: Boolean
    )
}