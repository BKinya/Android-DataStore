package com.beatrice.datastore_example


import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.beatrice.datastore_example.data.Settings
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

const val DATASTORE_NAME = "settings"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)
val NAME_KEY = stringPreferencesKey("name")

val Context.protoDataStore: DataStore<UserSettingsOuterClass.UserSettings> by dataStore(
    "settings.pb",
    UserSettingsSerializer
)

class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private val viewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.helloTextView)
        writeSettings()
        getSettings()
        observeName()
    }

    fun writeName(){
        viewModel.writeName(this)
    }

    fun getSettings(){
        viewModel.getSettings(this)
    }
    fun writeSettings(){
        viewModel.writeSettings(this)
    }
    fun getName(){
        viewModel.getName(context = this)
    }

    fun observeName(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.name.collect { name->
                    textView.text = name ?: "My name"
                }
            }
        }
    }

}