package com.example.storynotes

import com.example.storynotes.characters.Character
import com.example.storynotes.locations.Location
import com.example.storynotes.scenes.Scene
//Dátová trieda reprezentujúca jeden projekt/príbeh so zoznamom postáv, lokácii, atd
data class StoryProject(
    var name: String,
    var synopsis: String = "",
    val characters: MutableList<Character> = mutableListOf(),
    val locations: MutableList<Location> = mutableListOf(),
    val scenes: MutableList<Scene> = mutableListOf(),
    val notes: MutableList<String> = mutableListOf()
)