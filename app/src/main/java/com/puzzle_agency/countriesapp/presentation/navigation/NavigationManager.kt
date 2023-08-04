package com.puzzle_agency.countriesapp.presentation.navigation

import javax.inject.Singleton
import com.ramcosta.composedestinations.spec.Direction

@Singleton
class NavigationManager {
    private var navEventListener: ((destination: NavigationEvent) -> Unit)? = null

    fun navigate(destination: NavigationEvent) {
        navEventListener?.invoke(destination)
    }

    fun setOnNavEvent(navEventListener: (destination: NavigationEvent) -> Unit) {
        this.navEventListener = navEventListener
    }

    private var onBackClick: () -> Unit? = {}

    fun navigateBack() {
        onBackClick()
    }

    fun setOnBackEvent(onBackClick: () -> Unit) {
        this.onBackClick = onBackClick
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