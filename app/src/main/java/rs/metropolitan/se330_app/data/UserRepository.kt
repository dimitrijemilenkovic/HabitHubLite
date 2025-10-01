package rs.metropolitan.se330_app.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
    
    suspend fun login(email: String, password: String): User? {
        return userDao.login(email, password)
    }
    
    suspend fun register(email: String, password: String, fullName: String): Result<User> {
        return try {
            // Provera da li korisnik već postoji
            val existingUser = userDao.getUserByEmail(email)
            if (existingUser != null) {
                return Result.failure(Exception("Email već postoji"))
            }
            
            val timestamp = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            
            val user = User(
                email = email,
                password = password, // U produkciji bi trebalo hash-ovati
                fullName = fullName,
                createdAt = timestamp
            )
            
            val userId = userDao.insertUser(user)
            Result.success(user.copy(id = userId))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    fun getUserById(userId: Long): Flow<User?> {
        return userDao.getUserById(userId)
    }
    
    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }
} 