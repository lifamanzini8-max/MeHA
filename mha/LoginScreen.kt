package com.example.mha


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    errorMessage: String?,
    isLoading: Boolean,
    onCreateAccount: () -> Unit = {},
    onLogin: (String, String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "MHA",
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                "Mental Health Awareness",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(8.dp))
            Text("Break the silence, speak your mind!")

            Spacer(Modifier.height(28.dp))

            Text(
                "Welcome back",
                style = MaterialTheme.typography.headlineSmall
            )

            Text("Sign in to your account")

            Spacer(Modifier.height(20.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email address") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation =
                    PasswordVisualTransformation(),
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth()
            )

            if (errorMessage != null) {
                Spacer(Modifier.height(12.dp))
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = { onLogin(email.trim(), password) },
                enabled = !isLoading &&
                        email.isNotBlank() &&
                        password.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Login")
                }
            }

            Spacer(Modifier.height(12.dp))

            TextButton(
                onClick = onCreateAccount,
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Don't have an account? Register")
            }

            Spacer(Modifier.height(16.dp))

            Text(
                "Forgot your password? Use the password reset " +
                        "feature when we add it."
            )

            Spacer(Modifier.height(12.dp))

            Text(
                "Mental Health Matters",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}