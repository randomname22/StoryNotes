package com.example.storynotes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

// Trieda na zobrazenie, pridávanie a odstraňovanie zápiskov
@Composable
fun NotesScreen(
    projectName: String,
    onBackClick: () -> Unit
) {

    val context = LocalContext.current
    val project = ProjectRepository.projects.find { it.name == projectName }
    var showDialog by rememberSaveable {mutableStateOf(false)}
    var noteText by rememberSaveable  {mutableStateOf("") }

    if (showDialog) {

        AlertDialog(
            onDismissRequest = {showDialog = false},

            title = {Text("Add Note") },

            text = {
                OutlinedTextField(
                    value = noteText,
                    onValueChange = {noteText = it },
                    label = {Text("Note") }
                )
            },

            confirmButton = {

                Button(
                    onClick = {
                        if (noteText.isNotBlank()) {
                            project?.notes?.add(noteText)

                            Storage.saveProjects(
                                context,
                                ProjectRepository.projects
                            )

                            noteText = ""
                            showDialog = false
                        }
                    }
                ) {
                    Text("Add")
                }
            },

            dismissButton = {

                TextButton(onClick = {showDialog = false }
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

        Button(onClick = onBackClick) {Text("← Back") }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Notes",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (project?.notes?.isEmpty() == true) {

            Text("No notes yet")
        }

        LazyColumn {

            items(
                project?.notes ?: emptyList()
            ) { note ->
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

                        Text(note)

                        Button(
                            onClick = {

                                project?.notes?.remove(note)

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

        Button(
            onClick = {showDialog = true}
        ) {
            Text("Add Note")
        }
    }
}