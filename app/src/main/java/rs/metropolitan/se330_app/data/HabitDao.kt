package rs.metropolitan.se330_app.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    
    @Query("SELECT * FROM habit_entries WHERE date = :date")
    fun getEntryByDate(date: String): Flow<HabitEntry?>
    
    @Query("SELECT * FROM habit_entries ORDER BY date DESC")
    fun getAllEntries(): Flow<List<HabitEntry>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: HabitEntry)
    
    @Update
    suspend fun updateEntry(entry: HabitEntry)
    
    @Delete
    suspend fun deleteEntry(entry: HabitEntry)
    
    @Query("SELECT * FROM habit_entries WHERE date >= :startDate AND date <= :endDate ORDER BY date DESC")
    fun getEntriesInRange(startDate: String, endDate: String): Flow<List<HabitEntry>>
} 