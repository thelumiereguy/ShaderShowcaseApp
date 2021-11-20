package com.thelumiereguy.shadershowcase.core.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "wallpaper_preferences")

class PreferenceManager(private var context: Context?) {

    private val selectedShaderId = "selectedShaderId"

    private val selectedShaderIdPreference = intPreferencesKey(selectedShaderId)

    fun getSelectedShader(): Flow<Int>? {
        return context?.dataStore?.data?.map {
            it[selectedShaderIdPreference] ?: 0
        }
    }

    suspend fun setSelectedShader(shaderId: Int) {
        context?.dataStore?.edit {
            it[selectedShaderIdPreference] = shaderId
        }
    }

    fun release() {
        context = null
    }
}