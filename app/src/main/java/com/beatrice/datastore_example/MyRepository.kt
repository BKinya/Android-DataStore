package com.beatrice.datastore_example

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import com.beatrice.datastore_example.data.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MyRepository {

    fun getSettings(context: Context): Flow<UserSettingsOuterClass.UserSettings>{
       return context.protoDataStore.data
    }

    suspend fun writeSettings(context: Context){
        context.protoDataStore.updateData { settings ->
            settings.toBuilder()
                .setNickname("Lana")
                .setName("Lana Bingham")
                .setEmail("lans@email.com")
                .build()

        }
    }

   suspend fun writeName(context: Context){
        context.dataStore.edit { settings ->
            settings[NAME_KEY] = "John Doe"
        }
    }

    suspend fun getName(context: Context):Flow<String?>{
        return context.dataStore.data.map { settings ->
            val name = settings[NAME_KEY]
            name
        }
    }
}