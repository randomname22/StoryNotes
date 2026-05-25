package com.example.storynotes

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Trieda na zobrazenie, pridávanie a odstraňovanie projektov/príbehov
@Composable
fun ProjectDetailScreen(
    projectName: String,
    onBackClick: () -> Unit,
    onSynopsisClick: () -> Unit,
    onCharactersClick: () -> Unit,
    onLocationsClick: () -> Unit,
    onScenesClick: () -> Unit,
    onNotesClick: () -> Unit
) {

    val project =
        ProjectRepository.projects.find {it.name == projectName }

    if (project == null) {Text("Project not found")
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Button(onClick = onBackClick) {Text("← Back")}

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = project.name,)
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onSynopsisClick) {Text("Synopsis") }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onCharactersClick) {Text("Characters") }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onLocationsClick) { Text("Locations") }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onScenesClick) { Text("Scenes") }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onNotesClick) { Text("Notes") }
    }
}