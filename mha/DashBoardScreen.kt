package com.example.mha

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ClientDashboard(
    email: String,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "MHA",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            "Your Mental Health Journey Starts Here",
            style = MaterialTheme.typography.titleLarge
        )

        Text("Welcome, $email")

        HorizontalDivider()

        DashboardCard(
            "Mental Health Chatbot",
            "Access your mental health support chatbot."
        )

        DashboardCard(
            "Self-Assessments",
            "Complete available wellbeing assessments."
        )

        DashboardCard(
            "Resources & Guides",
            "Explore mental health articles and guides."
        )

        DashboardCard(
            "Community",
            "Explore peer-support features."
        )

        DashboardCard(
            "My Profile",
            "View your account information."
        )

        Button(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Logout")
        }
    }
}

@Composable
private fun DashboardCard(
    title: String,
    description: String
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(description)
        }
    }
}