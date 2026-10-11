package com.example.mha

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    userName: String,
    onAssessmentsClick: () -> Unit,
    onResourcesClick: () -> Unit,
    onCommunityClick: () -> Unit,
    onChatSupportClick: () -> Unit,
    onLogout: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "MHA",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Mental Health Awareness",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Break the silence, speak your mind!",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Welcome, $userName!",
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "Your mental health journey starts here.",
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onAssessmentsClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Self Assessments")
            }

            OutlinedButton(
                onClick = onResourcesClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Resources & Guides")
            }

            OutlinedButton(
                onClick = onCommunityClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Peer Community")
            }

            OutlinedButton(
                onClick = onChatSupportClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Chat Support")
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedButton(onClick = onLogout) {
                Text("Log Out")
            }
        }
    }
}
