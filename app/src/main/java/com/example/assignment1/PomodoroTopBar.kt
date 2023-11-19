package com.example.assignment1

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.assignment1.data.Settings
import com.example.assignment1.ui.AppViewModelProvider
import com.example.assignment1.ui.navbar.NavbarViewModel
import com.example.assignment1.ui.navigation.DropDownNavigation
import com.example.assignment1.ui.unlockables.UnlockableStoreDestination
import com.example.assignment1.ui.visuals.MetallicContainer


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PomodoroTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {},
    navController: NavController,
    viewModel: NavbarViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val unlockables : Settings by viewModel.settingsUiState.collectAsState()
    var expanded by remember { mutableStateOf(false) }

    Column() {
        Box(
            modifier = Modifier.background(
                brush = Brush.verticalGradient(listOf(Color.White, Color.Gray)),
                shape = RoundedCornerShape(0.dp))
        ) {
            CenterAlignedTopAppBar(
                title = { Text(title) },
                modifier = modifier,
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.Black
                ),
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    if (navController.previousBackStackEntry != null) {
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
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color.Gray, Color.LightGray, Color.Gray),
                        start = (Offset(0f, 0.0f)),
                        end = (Offset(700f, 500f))
                    ),
                shape = RoundedCornerShape(0.dp))
                .padding(5.dp),
            horizontalArrangement = Arrangement.End
        ) {
            CurrencyDisplay(
                currency = unlockables.currency
            ) { navController.navigate(UnlockableStoreDestination.route) }
            DropDownNavigation(
                navController = navController,
                expanded = expanded,
                currentRoute = navController.currentDestination?.route.toString()
            ) {
                expanded = !expanded
            }

        }
        Spacer(modifier = Modifier.height(1.dp)
            .background(
            brush = Brush.horizontalGradient(listOf(Color.White, Color.Gray)),
        )
            .fillMaxWidth())


    }

}

@Composable
fun CurrencyDisplay(
    currency: Int,
    navigateToStore: () -> Unit
) {
    MetallicContainer(height = 5f, rounding = 6.dp) {
        Row(
            modifier = Modifier
                .clickable { navigateToStore() }
                .padding(5.dp),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.coin_svgrepo_com),
                contentDescription = "Image of coin with dollar sign",
                modifier = Modifier
                    .size(18.dp)
                    .align(Alignment.CenterVertically)
            )
            Text(
                text = "$currency",
                fontSize = 18.sp,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
        }
    }
}