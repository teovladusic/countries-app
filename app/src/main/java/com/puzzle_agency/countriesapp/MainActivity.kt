package com.puzzle_agency.countriesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.puzzle_agency.countriesapp.presentation.NavGraphs
import com.puzzle_agency.countriesapp.presentation.navigation.NavigationManager
import com.puzzle_agency.countriesapp.ui.theme.CountriesAppTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountriesAppTheme {
                // A surface container using the 'background' color from the theme
                val controller = rememberNavController()

                LaunchedEffect(key1 = Unit) {
                    initNavManager(controller)
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    DestinationsNavHost(
                        navController = controller,
                        navGraph = NavGraphs.root,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }

    private fun initNavManager(controller: NavHostController) {

        navigationManager.setOnNavEvent {
            val route = it.direction.route

            if (it.popUpData != null) {
                controller.navigate(route, builder = {
                    popUpTo(it.popUpData.popupTo,
                        popUpToBuilder = { inclusive = it.popUpData.inclusive })
                })

                return@setOnNavEvent
            }

            controller.navigate(route)
        }
    }
}