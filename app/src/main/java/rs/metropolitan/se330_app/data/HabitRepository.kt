package rs.metropolitan.se330_app.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HabitRepository @Inject constructor(
    private val habitDao: HabitDao
) {
    fun getEntryByDate(date: String): Flow<HabitEntry?> = 
        habitDao.getEntryByDate(date)
    
    fun getAllEntries(): Flow<List<HabitEntry>> = 
        habitDao.getAllEntries()
    
    suspend fun insertOrUpdateEntry(entry: HabitEntry) {
        habitDao.insertEntry(entry)
    }
    
    suspend fun deleteEntry(entry: HabitEntry) {
        habitDao.deleteEntry(entry)
    }
    
    fun getEntriesInRange(startDate: String, endDate: String): Flow<List<HabitEntry>> =
        habitDao.getEntriesInRange(startDate, endDate)
} 