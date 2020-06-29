package com.example.listmaker

import android.content.Context
import androidx.preference.PreferenceManager

class ListDataManager(private val context: Context) {

    fun saveList(list: TaskList) {

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context).edit()
        sharedPreferences.putStringSet(list.name, list.tasks.toHashSet())
        sharedPreferences.apply()
    }

    fun readLists(): ArrayList<TaskList> {
        // reading doesn't need call .Editor
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val sharedPreferenceContents = sharedPreferences.all // as map

        val taskLists = ArrayList<TaskList>()

        // convert map of HashSet<String> to ArrayList of String
        for (taskList in sharedPreferenceContents) {
            val itemsHashSet = taskList.value as HashSet<String>
            val list = TaskList(taskList.key, ArrayList(itemsHashSet))

            taskLists.add(list)
        }

        return taskLists
    }
}