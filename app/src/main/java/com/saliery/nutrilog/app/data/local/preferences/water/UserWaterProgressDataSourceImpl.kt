package com.saliery.nutrilog.app.data.local.preferences.water

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.saliery.nutrilog.app.domain.model.user.DailyWaterProgress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import kotlinx.serialization.json.Json
import network.chaintech.kmp_date_time_picker.utils.now
import timber.log.Timber

private val Context.waterPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "water_progress_preferences"
)

class UserWaterProgressDataSourceImpl(
    private val context: Context,
    private val json: Json
) : UserWaterProgressDataSource {

    companion object {
        private val WATER_PROGRESS_KEY = stringPreferencesKey("water_progress_data")
    }

    private val dataStore = context.waterPreferencesDataStore

    override fun observeTodayProgress(): Flow<DailyWaterProgress> {
        return dataStore.data
            .map { preferences ->
                val waterProgressJson = preferences[WATER_PROGRESS_KEY]
                if (!waterProgressJson.isNullOrBlank()) {
                    try {
                        val currentData =  json.decodeFromString<DailyWaterProgress>(waterProgressJson)

                        if (currentData.currentDate == LocalDate.now()) {
                            currentData
                        } else {
                            DailyWaterProgress()
                        }
                    } catch (e: Exception) {
                        Timber.e(e, "Error decoding water progress from JSON")
                        DailyWaterProgress()
                    }
                } else {
                    DailyWaterProgress()
                }
            }
            .catch { e ->
                Timber.e(e, "Error reading from DataStore")
                emit(DailyWaterProgress())
            }
    }

    override suspend fun addWater(amountMl: Double) {
        try {
            dataStore.edit { preferences ->
                val currentProgressJson = preferences[WATER_PROGRESS_KEY]

                val currentProgress = if (!currentProgressJson.isNullOrBlank()) {
                    try {
                        json.decodeFromString<DailyWaterProgress>(currentProgressJson)
                    } catch (e: Exception) {
                        Timber.e(e.localizedMessage)
                        DailyWaterProgress()
                    }
                } else {
                    DailyWaterProgress()
                }

                val today = LocalDate.now()
                val newProgress = if (currentProgress.currentDate != today) {
                    DailyWaterProgress(
                        currentProgressMl = amountMl,
                        currentDate = today
                    )
                } else {

                    val newProgressMl = if (amountMl in 0.0..4000.0) {
                        currentProgress.currentProgressMl + amountMl
                    } else { currentProgress.currentProgressMl }

                    currentProgress.copy(
                        currentProgressMl = newProgressMl
                    )
                }

                val waterProgressJson = json.encodeToString(newProgress)
                preferences[WATER_PROGRESS_KEY] = waterProgressJson
            }
        } catch (e: Exception) {
            Timber.e(e.localizedMessage)
        }
    }

    override suspend fun resetTodayProgress() {
        try {
            dataStore.edit { preferences ->
                val waterProgressJson = json.encodeToString(
                    DailyWaterProgress(
                        currentProgressMl = 0.0,
                        currentDate = LocalDate.now()
                    )
                )
                preferences[WATER_PROGRESS_KEY] = waterProgressJson
            }
        } catch (e: Exception) {
            Timber.e(e.localizedMessage)
        }
    }
}