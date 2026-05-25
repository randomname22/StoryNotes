package com.example.storynotes.characters

//Dátová trieda predstavujúca jednu postavu
data class Character(
    var name: String,
    var personality: String = "",
    var appearance: String = "",
    var background: String = "",
    var relationships: String = ""
)