package com.saliery.nutrilog

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeDataStore : DataStore<Preferences> {
    private val _data = MutableStateFlow<Preferences>(emptyPreferences())

    override val data: Flow<Preferences> = _data.asStateFlow()

    override suspend fun updateData(
        transform: suspend (t: Preferences) -> Preferences
    ): Preferences {
        val newValue = transform(_data.value)
        _data.value = newValue
        return newValue
    }
}