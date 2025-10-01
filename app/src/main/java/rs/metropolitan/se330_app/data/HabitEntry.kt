package rs.metropolitan.se330_app.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "habit_entries")
data class HabitEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: String,
    val waterGlasses: Int = 0,
    val sleepHours: Float = 0f,
    val walkMinutes: Int = 0
) 