package com.example.assignment1

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.assignment1.services.TimerService
import com.example.assignment1.ui.navigation.DropDownNavigation
import com.example.assignment1.ui.navigation.PomodoroNavHost

@Composable
fun PomodoroApp(
    navController: NavHostController = rememberNavController(),
    timerService: TimerService
) {
    PomodoroNavHost (
        navController = navController,
        timerService = timerService
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PomodoroTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {},
    navController: NavController
) {
    var expanded by remember { mutableStateOf(false) }
    Column() {
        CenterAlignedTopAppBar(
            title = { Text(title) },
            modifier = modifier,
            scrollBehavior = scrollBehavior,
            navigationIcon = {
                if (canNavigateBack) {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            },
            actions = {
                IconButton(onClick = {expanded = !expanded}) {
                    Icon(imageVector = Icons.Filled.Menu,contentDescription = null)
                }
            }
        )
        DropDownNavigation(navController = navController, expanded = expanded, currentRoute = navController.currentDestination?.route.toString()) {
            expanded = !expanded
        }
    }

}