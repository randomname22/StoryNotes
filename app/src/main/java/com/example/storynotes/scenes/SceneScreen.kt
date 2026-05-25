package com.example.storynotes.scenes

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

// Trieda na zobrazenie, pridávanie a odstraňovanie scén
@Composable
fun SceneScreen(
    projectName: String,
    navController: NavController,
    onBackClick: () -> Unit
) {

    val context = LocalContext.current

    val project = ProjectRepository.projects.find { it.name == projectName }

    var showDialog by rememberSaveable {mutableStateOf(false)}

    var sceneName by rememberSaveable  {mutableStateOf("")}



    if (showDialog) {

        AlertDialog(
            onDismissRequest = {showDialog = false},

            title = {Text("Add Scene")},

            text = {
                OutlinedTextField(
                    value = sceneName,
                    onValueChange = {
                        sceneName = it
                    },
                    label = {Text("Scene name")}
                )
            },

            confirmButton = {

                Button(
                    onClick = {

                        if (sceneName.isNotBlank()) {

                            project?.scenes?.add(
                                Scene(name = sceneName)
                            )

                            Storage.saveProjects(
                                context,
                                ProjectRepository.projects
                            )

                            sceneName = ""
                            showDialog = false
                        }
                    }
                ) {
                    Text("Add")
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {showDialog = false }
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

        Button(onClick = onBackClick) { Text("← Back") }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Scenes",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (project?.scenes?.isEmpty() == true) {
            Text("No scenes yet")
        }

        LazyColumn {
            items(
                project?.scenes ?: emptyList()
            ) { scene ->

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
                                    "sceneDetail/${project?.name}/${scene.name}"
                                )
                            }
                        ) {
                            Text(scene.name)
                        }

                        Button(
                            onClick = {

                                project?.scenes?.remove(scene)

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

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Button(onClick = {showDialog = true}
        ) {
            Text("Add Scene")
        }
    }
}