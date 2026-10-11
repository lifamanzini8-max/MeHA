package com.example.mha

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun ClientNavigationScreen(userName: String, onLogout: () -> Unit) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route ?: "home"

    Column(Modifier.fillMaxSize()) {
        MhaNavigationBar(currentRoute = currentRoute) { route ->
            if (route != currentRoute) {
                navController.navigate(route) {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                }
            }
        }
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.weight(1f)
        ) {
            composable("home") { MhaAppScreen(onAboutClick = { navController.navigate("about") }) }
            composable("about") { AboutUsScreen(onNavigateBack = { navController.popBackStack() }) }
            composable("chat") { ChatSupportApp() }
            composable("resources") { ResourcesScreen() }
            composable("team") { TeamGridScreen() }
        }
    }
}

@Composable
fun MhaNavigationBar(currentRoute: String, onNavigate: (String) -> Unit) {
    val destinations = listOf(
        "home" to "Home",
        "about" to "About",
        "chat" to "Chat Support",
        "resources" to "Resources",
        "team" to "Our Team"
    )
    Row(
        modifier = Modifier.fillMaxWidth()
            .background(Color(0xFF232A45))
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 6.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        destinations.forEach { (route, label) ->
            TextButton(
                onClick = { onNavigate(route) },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = if (currentRoute == route) Color(0xFFFFD7A3) else Color.White
                )
            ) {
                Text(label, fontWeight = if (currentRoute == route) FontWeight.Bold else FontWeight.Normal)
            }
        }
    }
}
