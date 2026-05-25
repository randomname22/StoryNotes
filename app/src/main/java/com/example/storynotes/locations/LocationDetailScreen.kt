package com.example.storynotes.locations

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.storynotes.ProjectRepository
import com.example.storynotes.Storage

//Trieda na zobrazenie a úpravu lokácií
@Composable
fun LocationDetailScreen(
    projectName: String,
    locationName: String,
    onBackClick: () -> Unit
) {

    val context = LocalContext.current

    val project = ProjectRepository.projects.find {
            it.name == projectName
        }

    val location = project?.locations?.find {
            it.name == locationName
        }

    if (location == null) {
        Text("Location not found")
        return
    }

    var name by rememberSaveable {mutableStateOf(location.name)}

    var description by rememberSaveable {mutableStateOf(location.description) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Button(
            onClick = onBackClick) { Text("← Back") }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Location Detail",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = {name = it},
            label = {Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = description,
            onValueChange = {description = it},
            label = {Text("Description")},
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                location.name = name
                location.description = description

                Storage.saveProjects(
                    context,
                    ProjectRepository.projects
                )

                onBackClick()
            }
        ) {
            Text("Save")
        }
    }
}