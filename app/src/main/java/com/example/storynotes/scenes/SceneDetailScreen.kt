package com.example.storynotes.scenes

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.storynotes.ProjectRepository
import com.example.storynotes.Storage
import kotlin.collections.find

//Trieda na zobrazenie a úpravu scén
@Composable
fun SceneDetailScreen(
    projectName: String,
    sceneName: String,
    onBackClick: () -> Unit
) {

    val context = LocalContext.current

    val project = ProjectRepository.projects.find { it.name == projectName }

    val scene = project?.scenes?.find { it.name == sceneName }

    if (scene == null) {
        Text("Scene not found")
        return
    }

    var name by rememberSaveable  { mutableStateOf(scene.name) }
    var description by rememberSaveable  { mutableStateOf(scene.description) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Button(
            onClick = onBackClick) {Text("← Back") }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Scene Detail",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
            },
            label = {Text("Name")},
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = description,
            onValueChange = {
                description = it
            },
            label = {Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {

                scene.name = name
                scene.description = description

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