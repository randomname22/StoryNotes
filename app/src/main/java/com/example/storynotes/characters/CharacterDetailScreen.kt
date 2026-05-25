package com.example.storynotes.characters

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.storynotes.ProjectRepository
import com.example.storynotes.Storage

//Trieda na zobrazenie a úpravu postáv
@Composable
fun CharacterDetailScreen(
    projectName: String,
    characterName: String,
    onBackClick: () -> Unit
) {

    val context = LocalContext.current
    val project = ProjectRepository.projects.find {it.name == projectName}
    val character = project?.characters?.find { it.name == characterName }

    if (character == null) {Text("Character not found")
        return
    }

    var name by rememberSaveable  {mutableStateOf(character.name)}
    var personality by rememberSaveable  {mutableStateOf(character.personality) }
    var appearance by rememberSaveable  {mutableStateOf(character.appearance) }
    var background by rememberSaveable  {mutableStateOf(character.background) }
    var relationships by rememberSaveable  {mutableStateOf(character.relationships) }
//-------------------------------
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Button(onClick = onBackClick) { Text("← Back") }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Character Detail",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = {name = it},
            label = {Text("Name")},
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = personality,
            onValueChange = {personality = it},
            label = {Text("Personality")},
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = appearance,
            onValueChange = {appearance = it },
            label = {Text("Appearance")},
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = background,
            onValueChange = {background = it },
            label = {Text("Background")},
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = relationships,
            onValueChange = {relationships = it },
            label = {Text("Relationships")},
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                character.name = name
                character.personality = personality
                character.appearance = appearance
                character.background = background
                character.relationships = relationships

                Storage.saveProjects(context, ProjectRepository.projects)
                onBackClick()

            }
        ) {
            Text("Save")
        }
    }
}