package com.example.storynotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.storynotes.characters.CharacterDetailScreen
import com.example.storynotes.characters.CharacterScreen
import com.example.storynotes.locations.LocationDetailScreen
import com.example.storynotes.locations.LocationScreen
import com.example.storynotes.scenes.SceneDetailScreen
import com.example.storynotes.scenes.SceneScreen
import com.example.storynotes.ui.theme.StoryNotesTheme

/**
 * Hlavná aktivita aplikácie.
 * Pri spustení načíta uložené projekty a zobrazí navigáciu aplikácie.
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ProjectRepository.projects.clear()
        ProjectRepository.projects.addAll(Storage.loadProjects(this))
        setContent {StoryNotesTheme() {StoryNotesNavigation()}
        }
    }
}

//Navigácia medzi obrazovkami
@Composable
fun StoryNotesNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "projects"
    ) {

        composable("projects") {StoryNotesApp(navController)}

        composable(
            route = "detail/{projectName}",
            arguments = listOf(navArgument("projectName") {type = NavType.StringType})
        ) { backStackEntry ->

            val projectName = backStackEntry.arguments?.getString("projectName") ?: "Unknown"

            ProjectDetailScreen(projectName = projectName,

                onBackClick = {navController.popBackStack()},
                onSynopsisClick = {navController.navigate("synopsis/$projectName")},
                onCharactersClick = {navController.navigate("characters/$projectName") },
                onLocationsClick = {navController.navigate("locations/$projectName")},
                onScenesClick = {navController.navigate("scenes/$projectName")},
                onNotesClick = {navController.navigate("notes/$projectName")}
            )
        }


        composable(
            route = "characters/{projectName}",
            arguments = listOf(navArgument("projectName") {type = NavType.StringType})
        ) {backStackEntry ->

            val projectName = backStackEntry.arguments?.getString("projectName") ?: "Unknown"

            CharacterScreen(
                projectName = projectName,
                navController = navController,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "synopsis/{projectName}",
            arguments = listOf(
                navArgument("projectName") {type = NavType.StringType}
            )
        ) { backStackEntry ->

            val projectName = backStackEntry.arguments?.getString("projectName") ?: "Unknown"

            SynopsisScreen(
                projectName = projectName,

                onBackClick = {navController.popBackStack()}
            )
        }

        composable(
            route = "locations/{projectName}",
            arguments = listOf(navArgument("projectName") {type = NavType.StringType}
            )
        ) { backStackEntry ->

            val projectName = backStackEntry.arguments?.getString("projectName") ?: "Unknown"

            LocationScreen(
                projectName = projectName,

                onBackClick = {navController.popBackStack() },
                navController = navController
            )
        }

        composable(
            route = "scenes/{projectName}",
            arguments = listOf(navArgument("projectName") {type = NavType.StringType}
            )
        ) { backStackEntry ->

            val projectName = backStackEntry.arguments?.getString("projectName") ?: "Unknown"

            SceneScreen(
                projectName = projectName,
                navController = navController,
                onBackClick = {navController.popBackStack()}
            )
        }

        composable(
            route = "notes/{projectName}",
            arguments = listOf(navArgument("projectName") {type = NavType.StringType}
            )
        ) { backStackEntry ->

            val projectName = backStackEntry.arguments?.getString("projectName") ?: "Unknown"

            NotesScreen(
                projectName = projectName,
                onBackClick = {navController.popBackStack()}
            )
        }

        composable(
            route = "characterDetail/{projectName}/{characterName}",

            arguments = listOf(
                navArgument("projectName") {type = NavType.StringType},
                navArgument("characterName") {type = NavType.StringType}
            )

        ) { backStackEntry ->

            val projectName =
                backStackEntry.arguments?.getString("projectName") ?: ""

            val characterName = backStackEntry.arguments?.getString("characterName") ?: ""

            CharacterDetailScreen(

                projectName = projectName,
                characterName = characterName,
                onBackClick = {navController.popBackStack()}
            )
        }


        composable(
            route = "locationDetail/{projectName}/{locationName}",

            arguments = listOf(

                navArgument("projectName") {type = NavType.StringType},

                navArgument("locationName") {type = NavType.StringType}
            )

        ) { backStackEntry ->

            val projectName = backStackEntry.arguments?.getString("projectName") ?: ""

            val locationName = backStackEntry.arguments?.getString("locationName") ?: ""

            LocationDetailScreen(

                projectName = projectName,
                locationName = locationName,
                onBackClick = {navController.popBackStack() }
            )
        }

        composable(
            route = "sceneDetail/{projectName}/{sceneName}",

            arguments = listOf(

                navArgument("projectName") {type = NavType.StringType },
                navArgument("sceneName") {type = NavType.StringType}
            )

        ) { backStackEntry ->

            val projectName = backStackEntry.arguments?.getString("projectName") ?: ""

            val sceneName = backStackEntry.arguments?.getString("sceneName") ?: ""

            SceneDetailScreen(

                projectName = projectName,
                sceneName = sceneName,
                onBackClick = {navController.popBackStack() }
            )
        }

    }
}

//Hlavná obrazovka aplikáciu.
//Umožňuje vytvátať a odstraňovať projekty/príbehy
@Composable
fun StoryNotesApp(
    navController: NavController
) {

    val context = LocalContext.current

    var showDialog by rememberSaveable  {mutableStateOf(false) }
    var projectName by rememberSaveable  {mutableStateOf("") }

    if (showDialog) {

        AlertDialog(
            onDismissRequest = {
                showDialog = false
            },

            title = {
                Text("Add Story")
            },

            text = {
                OutlinedTextField(
                    value = projectName,
                    onValueChange = {projectName = it },
                    label = {Text("Story name") }
                )
            },

            confirmButton = {
                Button(
                    onClick = {

                        if (projectName.isNotBlank()) {

                            ProjectRepository.projects.add(
                                StoryProject(name = projectName)
                            )

                            Storage.saveProjects(
                                context,
                                ProjectRepository.projects
                            )

                            projectName = ""
                            showDialog = false
                        }
                    }
                ) {
                    Text("Add")
                }
            },

            dismissButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        projectName = ""
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showDialog = true
                }
            ) {
                Text("+")
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            Text(
                text = "Stories",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (ProjectRepository.projects.isEmpty()) {
                Text(text = "No stories created yet.")

            } else {
                LazyColumn {

                    items(ProjectRepository.projects) { project ->

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
                                            "detail/${project.name}"
                                        )
                                    }
                                ) {
                                    Text(project.name)
                                }

                                Button(
                                    onClick = {

                                        ProjectRepository.projects.remove(project)

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
        }
    }
}