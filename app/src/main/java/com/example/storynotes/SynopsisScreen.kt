package com.example.storynotes

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

// Trieda na zobrazenie synopsisu
@Composable
fun SynopsisScreen(
    projectName: String,
    onBackClick: () -> Unit
) {

    val context = LocalContext.current

    val project = ProjectRepository.projects.find {it.name == projectName}

    var synopsisText by rememberSaveable {mutableStateOf(project?.synopsis ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Button(
            onClick = onBackClick) {Text("← Back")}

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "$projectName - Synopsis")

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = synopsisText,
            onValueChange = {synopsisText = it },
            modifier = Modifier.fillMaxWidth(),
            label = {Text("Story description") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                project?.synopsis = synopsisText

                Storage.saveProjects(
                    context,
                    ProjectRepository.projects
                )
            }
        ) {
            Text("Save")
        }
    }
}