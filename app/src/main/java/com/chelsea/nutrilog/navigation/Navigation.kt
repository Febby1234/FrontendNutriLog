package com.chelsea.nutrilog.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chelsea.nutrilog.auth.AuthViewModel
import com.chelsea.nutrilog.auth.LoginScreen
import com.chelsea.nutrilog.auth.RegisterScreen
import com.chelsea.nutrilog.dashboard.DashboardScreen
import com.chelsea.nutrilog.dashboard.DashboardViewModel
import com.chelsea.nutrilog.foodLog.FoodLogScreen
import com.chelsea.nutrilog.foodLog.FoodViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutriLogApp() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = hiltViewModel()
    val authState by authViewModel.uiState.collectAsState()
    
    if (authState.isLoggedIn) {
        AppWithNavigation(navController, authViewModel)
    } else {
        AuthNavigation(navController, authViewModel)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AuthNavigation(navController: NavHostController, authViewModel: AuthViewModel) {
    NavHost(navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                viewModel = authViewModel,
                onLoginSuccess = {
                    navController.navigate("dashboard") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate("register")
                }
            )
        }
        
        composable("register") {
            RegisterScreen(
                viewModel = authViewModel,
                onRegisterSuccess = {
                    navController.navigate("dashboard") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AppWithNavigation(navController: NavHostController, authViewModel: AuthViewModel) {
    var showDrawer by remember { mutableStateOf(false) }
    
    ModalNavigationDrawer(
        drawerContent = {
            DrawerContent(
                onDashboard = {
                    navController.navigate("dashboard")
                    showDrawer = false
                },
                onLogFood = {
                    navController.navigate("log_food")
                    showDrawer = false
                },
                onLogout = {
                    authViewModel.logout()
                    navController.navigate("login") {
                        popUpTo("dashboard") { inclusive = true }
                    }
                    showDrawer = false
                }
            )
        },
        scrimColor = Color.Black.copy(alpha = 0.32f),
        gesturesEnabled = true,
        drawerState = rememberDrawerState(DrawerValue.Closed)
    ) {
        NavHost(navController, startDestination = "dashboard") {
            composable("dashboard") {
                val viewModel: DashboardViewModel = hiltViewModel()
                DashboardScreen(viewModel = viewModel)
            }
            
            composable("log_food") {
                val viewModel: FoodViewModel = hiltViewModel()
                FoodLogScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DrawerContent(
    onDashboard: () -> Unit,
    onLogFood: () -> Unit,
    onLogout: () -> Unit
) {
    Column(modifier = Modifier.padding(top = 16.dp)) {
        NavigationDrawerItem(
            label = { Text("Dashboard") },
            icon = { Icon(Icons.Default.Dashboard, contentDescription = "Dashboard") },
            selected = false,
            onClick = onDashboard,
            colors = NavigationDrawerItemDefaults.colors(
                selectedContainerColor = Color(0xFF4CAF50).copy(alpha = 0.12f),
                unselectedTextColor = Color.Black
            )
        )
        
        NavigationDrawerItem(
            label = { Text("Log Food") },
            icon = { Icon(Icons.Default.Restaurant, contentDescription = "Log Food") },
            selected = false,
            onClick = onLogFood,
            colors = NavigationDrawerItemDefaults.colors(
                selectedContainerColor = Color(0xFF4CAF50).copy(alpha = 0.12f),
                unselectedTextColor = Color.Black
            )
        )
        
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        
        NavigationDrawerItem(
            label = { Text("Logout") },
            icon = { Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Logout") },
            selected = false,
            onClick = onLogout,
            colors = NavigationDrawerItemDefaults.colors(
                selectedContainerColor = Color.Red.copy(alpha = 0.12f),
                unselectedTextColor = Color.Red
            )
        )
    }
}
