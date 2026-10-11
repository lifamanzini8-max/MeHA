package com.example.mha

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                MhaApp()
            }
        }
    }
}

@Composable
fun MhaApp() {
    val auth = remember { FirebaseAuth.getInstance() }
    val db = remember { FirebaseFirestore.getInstance() }
    val repository = remember { AuthRepository() }
    val scope = rememberCoroutineScope()

    var showRegister by remember { mutableStateOf(false) }
    var registering by remember { mutableStateOf(false) }
    var registerError by remember { mutableStateOf<String?>(null) }

    var role by remember { mutableStateOf<String?>(null) }
    var email by remember { mutableStateOf("") }
    var isInitialLoading by remember { mutableStateOf(true) }
    var isLoggingIn by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    suspend fun loadUserRole() {
        errorMessage = null

        try {
            val currentUser = auth.currentUser
                ?: throw IllegalStateException(
                    "Please log in to continue."
                )

            val document = db.collection("users")
                .document(currentUser.uid)
                .get()
                .await()

            val fetchedRole = document.getString("role")

            if (fetchedRole != "admin" && fetchedRole != "client") {
                auth.signOut()
                role = null
                email = ""

                throw IllegalStateException(
                    "No valid account role was found. " +
                            "Please contact the administrator."
                )
            }

            role = fetchedRole
            email = currentUser.email.orEmpty()

        } catch (e: Exception) {
            role = null
            email = ""
            errorMessage = e.message
                ?: "Unable to load your account. Please try again."
        }
    }

    // Check for an existing signed-in user.
    LaunchedEffect(Unit) {
        if (auth.currentUser != null) {
            loadUserRole()
        }

        isInitialLoading = false
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        when {
            // 1. Initial loading screen
            isInitialLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            // 2. Registration screen
            showRegister -> {
                RegisterScreen(
                    onRegister = { name, enteredEmail, password ->
                        scope.launch {
                            registering = true
                            registerError = null

                            try {
                                repository.registerClient(
                                    fullName = name,
                                    email = enteredEmail.trim(),
                                    password = password
                                )

                                // Return to login after registration.
                                // Firebase signs in the newly created user,
                                // so sign out before returning to login.
                                auth.signOut()

                                showRegister = false
                                errorMessage =
                                    "Account created. Please log in."

                            } catch (e: Exception) {
                                registerError = e.message
                                    ?: "Registration failed. Please try again."
                            } finally {
                                registering = false
                            }
                        }
                    },
                    onBackToLogin = {
                        showRegister = false
                        registerError = null
                    },
                    loading = registering,
                    errorMessage = registerError
                )
            }

            // 3. Admin dashboard
            role == "admin" -> {
                AdminDashboard(
                    email = email,
                    onLogout = {
                        auth.signOut()
                        role = null
                        email = ""
                        errorMessage = null
                    }
                )
            }

            // 4. Client area: previous full home page plus About, Chat, Resources and Team.
            role == "client" -> {
                ClientNavigationScreen(
                    userName = email.substringBefore("@"),
                    onLogout = {
                        auth.signOut()
                        role = null
                        email = ""
                        errorMessage = null
                    }
                )
            }

            // 5. Login screen
            else -> {
                LoginScreen(
                    errorMessage = errorMessage,
                    isLoading = isLoggingIn,
                    onCreateAccount = {
                        registerError = null
                        errorMessage = null
                        showRegister = true
                    },
                    onLogin = { enteredEmail, enteredPassword ->
                        scope.launch {
                            isLoggingIn = true
                            errorMessage = null

                            try {
                                auth.signInWithEmailAndPassword(
                                    enteredEmail.trim(),
                                    enteredPassword
                                ).await()

                                loadUserRole()

                            } catch (e: Exception) {
                                auth.signOut()
                                role = null
                                email = ""

                                errorMessage =
                                    "Login failed. Check your email, " +
                                            "password, internet connection, " +
                                            "and account permissions."
                            } finally {
                                isLoggingIn = false
                            }
                        }
                    }
                )
            }
        }
    }
}