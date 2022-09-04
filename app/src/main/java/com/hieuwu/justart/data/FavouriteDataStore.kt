package com.hieuwu.justart.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow
import java.io.IOException

private const val FAVOURITE_PREFERENCES_NAME = "favourite_preferences"

// Create a DataStore instance using the preferencesDataStore delegate, with the Context as
// receiver.
private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(
    name = FAVOURITE_PREFERENCES_NAME
)

class FavouriteDataStore(context: Context) {
    private val FAVOURITE_MANAGER = stringSetPreferencesKey("favourite_manager")

    suspend fun saveArtWorkToDataStore(setOfId: Set<String>, context: Context) {
        context.dataStore.edit { preferences ->
            preferences[FAVOURITE_MANAGER] = setOfId
        }
    }

    val preferenceFlow: Flow<Set<String>> = context.dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[FAVOURITE_MANAGER] ?: mutableSetOf()
        }
}