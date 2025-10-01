package rs.metropolitan.se330_app.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
    indices = [Index(value = ["email"], unique = true)]
)
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val email: String,
    val password: String, // U produkciji bi trebalo da bude hash-ovana
    val fullName: String,
    val createdAt: String // Format: yyyy-MM-dd HH:mm:ss
) 