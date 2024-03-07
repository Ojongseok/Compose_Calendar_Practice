package com.example.mildfistassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mildfistassignment.ui.theme.MildfistAssignmentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MildfistAssignmentTheme {
                val navController = rememberNavController()

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
                }
            }
        }
    }
}

enum class Destination(name: String) {
    CALENDAR("calendar"),
    CALENDAR_DETAIL("calendarDetail")
}