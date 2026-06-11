package com.chelsea.nutrilog.data.auth

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

// DataStore instance yang bisa diimport
private const val PREFERENCES_NAME = "auth_preferences"

val Context.dataStore by preferencesDataStore(name = PREFERENCES_NAME)
