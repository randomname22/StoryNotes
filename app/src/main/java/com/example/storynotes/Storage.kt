package com.example.storynotes

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

//Trieda zabezpečujúca ukladanie a načítanie projektov
//Trieda generovaná pomocou AI

object Storage {

    private const val PREFS_NAME = "story_notes"
    private const val PROJECTS_KEY = "projects"

    fun saveProjects(
        context: Context,
        projects: List<StoryProject>
    ) {

        val prefs =
            context.getSharedPreferences(
                PREFS_NAME,
                Context.MODE_PRIVATE
            )

        val json =
            Gson().toJson(projects)

        prefs.edit()
            .putString(PROJECTS_KEY, json)
            .apply()
    }

    fun loadProjects(
        context: Context
    ): MutableList<StoryProject> {

        val prefs =
            context.getSharedPreferences(
                PREFS_NAME,
                Context.MODE_PRIVATE
            )

        val json =
            prefs.getString(
                PROJECTS_KEY,
                null
            )

        if (json == null) {
            return mutableListOf()
        }

        val type =
            object : TypeToken<MutableList<StoryProject>>() {}.type

        return Gson().fromJson(
            json,
            type
        )
    }
}