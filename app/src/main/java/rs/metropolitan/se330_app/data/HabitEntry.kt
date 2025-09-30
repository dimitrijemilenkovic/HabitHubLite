package rs.metropolitan.se330_app.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "habit_entries")
data class HabitEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: String, // Format: yyyy-MM-dd
    val waterGlasses: Int = 0, // broj čaša vode
    val sleepHours: Float = 0f, // sati sna
    val walkMinutes: Int = 0 // minuti šetnje
) 