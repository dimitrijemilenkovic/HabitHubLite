package rs.metropolitan.se330_app.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [HabitEntry::class, User::class],
    version = 2,
    exportSchema = false
)
abstract class HabitDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
    abstract fun userDao(): UserDao
} 