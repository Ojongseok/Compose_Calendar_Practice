package com.example.mildfistassignment

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun MildfistNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Destination.CALENDAR.name
    ) {
        composable(route = Destination.CALENDAR.name) {
            CalendarScreen(navController = navController)
        }

        composable(route = Destination.CALENDAR_DETAIL.name) {
            CalendarDetailScreen(navController = navController)
        }

        composable(
            route = "${Destination.DATE.name}/{date}/{time}",
            arguments = listOf(
                navArgument("date") {
                    type = NavType.StringType
                },
                navArgument("time") {
                    type = NavType.IntType
                }
            )
        ) {
            DateScreen(
                navController = navController,
                date = it.arguments?.getString("date")!!,
                time = it.arguments?.getInt("time")!!
            )
        }
    }
}

enum class Destination(name: String) {
    CALENDAR("calendar"),
    CALENDAR_DETAIL("calendarDetail"),
    DATE("date")
}