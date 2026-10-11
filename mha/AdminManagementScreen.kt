package com.example.mha

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

private enum class AdminSection {
    HOME, CLIENTS, RESOURCES, REPORTS, ACTIVITIES, SETTINGS
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboard(
    email: String,
    onLogout: () -> Unit
) {
    var section by remember { mutableStateOf(AdminSection.HOME) }
    val adminDb = remember { FirebaseFirestore.getInstance() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MHA Administration") },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Logout"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Text(
                "Administrator: $email",
                modifier = Modifier.padding(16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                TextButton(onClick = { section = AdminSection.HOME }) {
                    Text("Home")
                }
                TextButton(onClick = { section = AdminSection.CLIENTS }) {
                    Text("Clients")
                }
                TextButton(onClick = { section = AdminSection.RESOURCES }) {
                    Text("Resources")
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                TextButton(onClick = { section = AdminSection.REPORTS }) {
                    Text("Reports")
                }
                TextButton(onClick = { section = AdminSection.ACTIVITIES }) {
                    Text("Activities")
                }
                TextButton(onClick = { section = AdminSection.SETTINGS }) {
                    Text("Settings")
                }
            }

            HorizontalDivider()

            when (section) {
                AdminSection.HOME -> AdminHome(
                    onClients = { section = AdminSection.CLIENTS },
                    onResources = { section = AdminSection.RESOURCES },
                    onReports = { section = AdminSection.REPORTS },
                    onActivities = { section = AdminSection.ACTIVITIES },
                    onSettings = { section = AdminSection.SETTINGS }
                )

                AdminSection.CLIENTS -> ClientsSection(adminDb = adminDb)
                AdminSection.RESOURCES -> ResourcesSection(adminDb = adminDb)
                AdminSection.REPORTS -> PlaceholderSection(
                    "Reports",
                    "Reports and usage summaries will appear here."
                )
                AdminSection.ACTIVITIES -> PlaceholderSection(
                    "Administrative Activities",
                    "An audit log will record administrative changes."
                )
                AdminSection.SETTINGS -> PlaceholderSection(
                    "Settings",
                    "App preferences and administrator management."
                )
            }
        }
    }
}

@Composable
private fun AdminHome(
    onClients: () -> Unit,
    onResources: () -> Unit,
    onReports: () -> Unit,
    onActivities: () -> Unit,
    onSettings: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Welcome, Administrator", style = MaterialTheme.typography.headlineSmall)
        Text("Manage the MHA platform.")

        Button(onClick = onClients, modifier = Modifier.fillMaxWidth()) {
            Text("View and Manage Clients")
        }
        Button(onClick = onResources, modifier = Modifier.fillMaxWidth()) {
            Text("Maintain Resources")
        }
        Button(onClick = onReports, modifier = Modifier.fillMaxWidth()) {
            Text("Reports")
        }
        Button(onClick = onActivities, modifier = Modifier.fillMaxWidth()) {
            Text("Administrative Activities")
        }
        Button(onClick = onSettings, modifier = Modifier.fillMaxWidth()) {
            Text("Settings")
        }
    }
}

@Composable
private fun ClientsSection(adminDb: FirebaseFirestore) {
    var clients by remember { mutableStateOf<List<Map<String, Any>>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            clients = adminDb.collection("users").get().await()
                .documents.map { document ->
                    document.data.orEmpty() + ("documentId" to document.id)
                }
        } catch (e: Exception) {
            error = e.message ?: "Could not load clients."
        } finally {
            loading = false
        }
    }

    Column(Modifier.padding(16.dp)) {
        Text("Registered Accounts", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))

        when {
            loading -> CircularProgressIndicator()
            error != null -> Text(
                "Error: $error",
                color = MaterialTheme.colorScheme.error
            )
            clients.isEmpty() -> Text("No accounts found.")
            else -> LazyColumn {
                items(clients) { client ->
                    val role = client["role"]?.toString() ?: "unknown"

                    Card(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text(
                                client["fullName"]?.toString()
                                    ?: "Name not provided",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(client["email"]?.toString() ?: "No email")
                            Text("Role: $role")
                            Text(
                                "UID: ${client["documentId"]}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ResourcesSection(adminDb: FirebaseFirestore) {
    var resources by remember { mutableStateOf<List<Map<String, Any>>>(emptyList()) }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var link by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    fun reload() {
        scope.launch {
            loading = true
            try {
                resources = adminDb.collection("resources").get().await()
                    .documents.map { doc ->
                        doc.data.orEmpty() + ("documentId" to doc.id)
                    }
                message = null
            } catch (e: Exception) {
                message = e.message ?: "Could not load resources."
            } finally {
                loading = false
            }
        }
    }

    LaunchedEffect(Unit) { reload() }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Manage Resources", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Resource title") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = link,
            onValueChange = { link = it },
            label = { Text("Resource URL (optional)") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (title.isBlank() || description.isBlank()) {
                    message = "Enter a title and description."
                } else {
                    scope.launch {
                        loading = true
                        try {
                            val data = mapOf(
                                "title" to title.trim(),
                                "description" to description.trim(),
                                "url" to link.trim(),
                                "updatedBy" to (
                                        FirebaseAuth.getInstance().currentUser?.uid ?: ""
                                        ),
                                "updatedAt" to System.currentTimeMillis()
                            )
                            adminDb.collection("resources").add(data).await()
                            title = ""
                            description = ""
                            link = ""
                            message = "Resource added."
                            reload()
                        } catch (e: Exception) {
                            message = e.message ?: "Could not add resource."
                        } finally {
                            loading = false
                        }
                    }
                }
            },
            enabled = !loading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Resource")
        }

        message?.let { Text(it) }
        HorizontalDivider(Modifier.padding(vertical = 12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Existing Resources", style = MaterialTheme.typography.titleMedium)
            TextButton(onClick = { reload() }) { Text("Refresh") }
        }

        if (loading) CircularProgressIndicator()

        LazyColumn {
            items(resources) { resource ->
                val id = resource["documentId"]?.toString().orEmpty()

                Card(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text(
                            resource["title"]?.toString() ?: "Untitled",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(resource["description"]?.toString().orEmpty())
                        Text(resource["url"]?.toString().orEmpty())

                        Row {
                            TextButton(onClick = {
                                title = resource["title"]?.toString().orEmpty()
                                description = resource["description"]?.toString().orEmpty()
                                link = resource["url"]?.toString().orEmpty()
                                message = "Edit the fields above, then save changes."
                            }) {
                                Text("Load to edit")
                            }

                            TextButton(onClick = {
                                scope.launch {
                                    try {
                                        adminDb.collection("resources")
                                            .document(id).delete().await()
                                        message = "Resource deleted."
                                        reload()
                                    } catch (e: Exception) {
                                        message = e.message ?: "Delete failed."
                                    }
                                }
                            }) {
                                Text("Delete")
                            }
                        }

                        TextButton(onClick = {
                            scope.launch {
                                try {
                                    adminDb.collection("resources")
                                        .document(id)
                                        .update(
                                            mapOf(
                                                "title" to title.trim(),
                                                "description" to description.trim(),
                                                "url" to link.trim(),
                                                "updatedBy" to (
                                                        FirebaseAuth.getInstance()
                                                            .currentUser?.uid ?: ""
                                                        ),
                                                "updatedAt" to System.currentTimeMillis()
                                            )
                                        ).await()
                                    message = "Resource updated."
                                    reload()
                                } catch (e: Exception) {
                                    message = e.message
                                        ?: "Update failed. Load a resource and check the fields."
                                }
                            }
                        }) {
                            Text("Save changes to this resource")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PlaceholderSection(title: String, description: String) {
    Column(Modifier.padding(16.dp)) {
        Text(title, style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        Text(description)
    }
}