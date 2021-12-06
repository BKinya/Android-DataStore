package com.beatrice.datastore_example

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beatrice.datastore_example.data.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MyViewModel: ViewModel() {
    private val _name = MutableStateFlow<String?>(null)
    val name: StateFlow<String?> = _name

    val repository = MyRepository()

    fun writeSettings(context: Context){
        viewModelScope.launch {
            repository.writeSettings(context)
        }
    }

    fun getSettings(context: Context){
        Log.d("SEETTINGS", "Getting here")
        viewModelScope.launch {
            repository.getSettings(context).collect { settings ->
                Log.d("SEETTINGS", "is $settings")
                val myString = "${settings.name} ${settings.nickname} ${settings.email}"
                _name.value = myString

            }
        }
    }
    fun getName(context: Context){
       viewModelScope.launch {
           repository.getName(context).collect {
               _name.value = it
           }
       }
    }

    fun writeName(context: Context){
        viewModelScope.launch {
            repository.writeName(context)
        }

    }
}