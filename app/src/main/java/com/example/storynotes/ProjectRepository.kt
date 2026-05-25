package com.example.storynotes

import androidx.compose.runtime.mutableStateListOf

//trieda reprezentujúca úložisko všetkých projektov

object ProjectRepository {
    val projects = mutableStateListOf<StoryProject>()
}