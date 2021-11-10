package com.thelumiereguy.shadershowcase.core.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

object PreferenceManager {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "wallpaper_preferences")

    private const val selectedShaderId = "selectedShaderId"

    private val selectedShaderIdPreference = intPreferencesKey(selectedShaderId)

    fun getSelectedShader(context: Context): Flow<Int> {
        return context.dataStore.data.map {
            it[selectedShaderIdPreference] ?: 0
        }
    }

    fun CoroutineScope.setSelectedShader(context: Context, shaderId: Int) {
        launch {
            context.dataStore.edit {
                it[selectedShaderIdPreference] = shaderId
            }
        }
    }


}