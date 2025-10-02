package rs.metropolitan.se330_app.presentation.habit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import rs.metropolitan.se330_app.data.HabitEntry
import rs.metropolitan.se330_app.data.HabitRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HabitViewModel @Inject constructor(
    private val repository: HabitRepository
) : ViewModel() {
    
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    
    private val _currentDate = MutableStateFlow(LocalDate.now())
    val currentDate: StateFlow<LocalDate> = _currentDate.asStateFlow()
    
    val currentEntry: StateFlow<HabitEntry?> = _currentDate
        .flatMapLatest { date ->
            repository.getEntryByDate(date.format(dateFormatter))
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            null
        )
    
    val allEntries: StateFlow<List<HabitEntry>> = repository.getAllEntries()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
    
    fun updateWater(glasses: Int) {
        viewModelScope.launch {
            val date = _currentDate.value.format(dateFormatter)
            val entry = currentEntry.value ?: HabitEntry(date = date)
            repository.insertOrUpdateEntry(entry.copy(waterGlasses = glasses))
        }
    }
    
    fun updateSleep(hours: Float) {
        viewModelScope.launch {
            val date = _currentDate.value.format(dateFormatter)
            val entry = currentEntry.value ?: HabitEntry(date = date)
            repository.insertOrUpdateEntry(entry.copy(sleepHours = hours))
        }
    }
    
    fun updateWalk(minutes: Int) {
        viewModelScope.launch {
            val date = _currentDate.value.format(dateFormatter)
            val entry = currentEntry.value ?: HabitEntry(date = date)
            repository.insertOrUpdateEntry(entry.copy(walkMinutes = minutes))
        }
    }
    
    fun changeDate(newDate: LocalDate) {
        _currentDate.value = newDate
    }
    
    fun goToPreviousDay() {
        _currentDate.value = _currentDate.value.minusDays(1)
    }
    
    fun goToNextDay() {
        _currentDate.value = _currentDate.value.plusDays(1)
    }
    
    fun goToToday() {
        _currentDate.value = LocalDate.now()
    }
} 