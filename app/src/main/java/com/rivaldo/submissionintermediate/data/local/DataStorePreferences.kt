package com.rivaldo.submissionintermediate.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.rivaldo.submissionintermediate.di.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class DataStorePreferences private constructor(val dataStore: DataStore<Preferences>) {

    private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")
    private val USER_ID_KEY = stringPreferencesKey("userId")
    private val USER_NAME_KEY = stringPreferencesKey("userName")
    private val TOKEN_KEY = stringPreferencesKey("token")


    fun getIsLoggedIn(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[IS_LOGIN_KEY] ?: false
        }
    }

   suspend fun setIsLoggedIn(isLoggedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_LOGIN_KEY] = isLoggedIn
        }
    }

    fun getUserID(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[USER_ID_KEY] ?: ""
        }
    }

    suspend fun setUserID(userID: String) {
        dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userID
        }
    }

    fun getUserName(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[USER_NAME_KEY] ?: ""
        }
    }

    suspend fun setUserName(userName: String) {
        dataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = userName
        }
    }

    fun getToken(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[TOKEN_KEY] ?: ""
        }
    }

    suspend fun setToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    companion object {
        public const val PREFERENCES_NAME = "com.rivaldo.submissionintermediate"

        @Volatile
        private var INSTANCE: DataStorePreferences? = null

        fun getInstance(context: Context): DataStorePreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = DataStorePreferences(context.dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}