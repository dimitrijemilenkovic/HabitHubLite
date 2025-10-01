package rs.metropolitan.se330_app.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import rs.metropolitan.se330_app.data.SessionManager
import rs.metropolitan.se330_app.data.User
import rs.metropolitan.se330_app.data.UserRepository
import javax.inject.Inject

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val user: User) : AuthState()
    data class Error(val message: String) : AuthState()
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sessionManager: SessionManager
) : ViewModel() {
    
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()
    
    val currentUserId: StateFlow<Long?> = sessionManager.userIdFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
    
    val currentUser: StateFlow<User?> = currentUserId
        .filterNotNull()
        .flatMapLatest { userId ->
            userRepository.getUserById(userId)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
    
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            
            try {
                if (email.isBlank() || password.isBlank()) {
                    _authState.value = AuthState.Error("Email i password ne mogu biti prazni")
                    return@launch
                }
                
                val user = userRepository.login(email.trim(), password)
                
                if (user != null) {
                    sessionManager.saveUserId(user.id)
                    _authState.value = AuthState.Success(user)
                } else {
                    _authState.value = AuthState.Error("Pogrešan email ili password")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Greška pri login-u")
            }
        }
    }
    
    fun register(email: String, password: String, fullName: String, confirmPassword: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            
            try {
                // Validacija
                if (email.isBlank() || password.isBlank() || fullName.isBlank()) {
                    _authState.value = AuthState.Error("Sva polja su obavezna")
                    return@launch
                }
                
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    _authState.value = AuthState.Error("Unesite validan email")
                    return@launch
                }
                
                if (password.length < 6) {
                    _authState.value = AuthState.Error("Password mora imati najmanje 6 karaktera")
                    return@launch
                }
                
                if (password != confirmPassword) {
                    _authState.value = AuthState.Error("Password-i se ne podudaraju")
                    return@launch
                }
                
                val result = userRepository.register(email.trim(), password, fullName.trim())
                
                result.fold(
                    onSuccess = { user ->
                        sessionManager.saveUserId(user.id)
                        _authState.value = AuthState.Success(user)
                    },
                    onFailure = { error ->
                        _authState.value = AuthState.Error(error.message ?: "Greška pri registraciji")
                    }
                )
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Greška pri registraciji")
            }
        }
    }
    
    fun logout() {
        viewModelScope.launch {
            sessionManager.clearSession()
            _authState.value = AuthState.Idle
        }
    }
    
    fun resetAuthState() {
        _authState.value = AuthState.Idle
    }
} 