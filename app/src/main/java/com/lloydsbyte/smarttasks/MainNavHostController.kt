package com.lloydsbyte.smarttasks

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lloydsbyte.smarttasks.ui.DetailScreen
import com.lloydsbyte.smarttasks.ui.HomeScreen

class MainNavKeys() {
    companion object {
        val Home = "home"
        val DetailScreen = "details"
    }
}


@Composable
fun MainNavHostController(
    innerPadding: PaddingValues,
    navHostController: NavHostController
) {

    NavHost(navController = navHostController, startDestination = MainNavKeys.Home){
        composable(route = MainNavKeys.Home) {
            HomeScreen(innerpadding = innerPadding, navHostController = navHostController)
        }

        composable(
            route = MainNavKeys.DetailScreen+"/{taskId}",
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, spring(
                    Spring.DampingRatioHighBouncy, Spring.StiffnessMedium) )
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700))
            }
        ) {  backstackEntry ->
            val taskId = backstackEntry.arguments?.getString("taskId")?:""
            DetailScreen(innerPadding = innerPadding, navHostController = navHostController, taskId = taskId)
        }
    }
}

/**
 * Enter Transitions
 * fadeIn(tween(700))
 * scaleIn(spring(Spring.DampingRatioHighBouncy))
 * slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700))
 * expandIn(tween(700, easing = LinearEasing))
 *
 * Exit Transitions
 * fadeOut(tween(700))
 * scaleOut(spring(Spring.DampingRatioHighBouncy))
 * slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700))
 * shrinkOut(tween(700, easing = LinearEasing))
 */