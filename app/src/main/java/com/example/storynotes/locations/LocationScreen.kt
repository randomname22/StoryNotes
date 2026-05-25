package com.example.storynotes.locations

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.storynotes.ProjectRepository
import com.example.storynotes.Storage

// Trieda na zobrazenie, pridávanie a odstraňovanie lokácií
@Composable
fun LocationScreen(
    projectName: String,
    onBackClick: () -> Unit,
    navController: NavController
) {

    val context = LocalContext.current

    val project =
        ProjectRepository.projects.find {
            it.name == projectName
        }

    var showDialog by rememberSaveable {mutableStateOf(false) }

    var locationName by rememberSaveable  {mutableStateOf("")}



    if (showDialog) {

        AlertDialog(
            onDismissRequest = {
                showDialog = false
            },

            title = {Text("Add Location")},

            text = {
                OutlinedTextField(
                    value = locationName,
                    onValueChange = {
                        locationName = it
                    },
                    label = {Text("Location name")}
                )
            },

            confirmButton = {

                Button(
                    onClick = {

                        if (locationName.isNotBlank()) {

                            project?.locations?.add(
                                Location(name = locationName)
                            )

                            Storage.saveProjects(
                                context,
                                ProjectRepository.projects
                            )

                            locationName = ""
                            showDialog = false
                        }
                    }
                ) {
                    Text("Add")
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {showDialog = false}
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Button(
            onClick = onBackClick
        ) {Text("← Back")}

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Locations",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (project?.locations?.isEmpty() == true) {

            Text(text = "No locations yet")
        }

        LazyColumn {

            items(
                project?.locations ?: emptyList()
            ) { location ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),

                            horizontalArrangement =
                                Arrangement.SpaceBetween
                        ) {

                            Button(
                                onClick = {
                                    navController.navigate(
                                        "locationDetail/${project?.name}/${location.name}"
                                    )
                                }
                            ) {

                                Text(location.name)
                            }

                            Button(
                                onClick = {

                                    project?.locations?.remove(
                                        location
                                    )

                                    Storage.saveProjects(
                                        context,
                                        ProjectRepository.projects
                                    )


                                }
                            ) {
                                Text("Delete")
                            }
                        }
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Button(
            onClick = { showDialog = true }
        ) {
            Text("Add Location")
        }
    }
}