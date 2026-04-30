package com.saliery.nutrilog.app.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.saliery.nutrilog.app.domain.model.user.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import timber.log.Timber

private val Context.userPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "user_preferences"
)

class UserPreferencesManager(
    private val context: Context,
    private val json: Json
) : UserLocalDataSource {

    companion object {
        private val USER_KEY = stringPreferencesKey("user_data")
    }

    private val dataStore = context.userPreferencesDataStore

    override fun observeUser(): Flow<User?> {
        return dataStore.data
            .map { preferences ->
                val userJson = preferences[USER_KEY]
                userJson?.let {
                    try {
                        json.decodeFromString<User>(it)
                    } catch (e: Exception) {
                        Timber.e(e, "Error decoding user from JSON")
                        null
                    }
                }
            }
            .catch { e ->
                Timber.e(e, "Error reading from DataStore")
                emit(null)
            }
    }

    override suspend fun getUser(): User? {
        return observeUser().firstOrNull()
    }

    override suspend fun saveUser(user: User) {
        try {
            dataStore.edit { preferences ->
                val userJson = json.encodeToString(user)
                preferences[USER_KEY] = userJson
            }
        } catch (e: Exception) {
            Timber.e(e, "Error saving user to DataStore")
            throw e
        }
    }
}