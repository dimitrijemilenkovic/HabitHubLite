package rs.metropolitan.se330_app.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_session")

@Singleton
class SessionManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val USER_ID_KEY = longPreferencesKey("user_id")
    
    val userIdFlow: Flow<Long?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_ID_KEY]
        }
    
    suspend fun saveUserId(userId: Long) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId
        }
    }
    
    suspend fun clearSession() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
    
    suspend fun isLoggedIn(): Boolean {
        var isLogged = false
        context.dataStore.data.map { preferences ->
            isLogged = preferences[USER_ID_KEY] != null
        }
        return isLogged
    }
} 