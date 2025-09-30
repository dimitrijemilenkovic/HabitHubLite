package rs.metropolitan.se330_app.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitTrackerScreen(
    viewModel: HabitViewModel = hiltViewModel()
) {
    val currentDate by viewModel.currentDate.collectAsState()
    val currentEntry by viewModel.currentEntry.collectAsState()
    val allEntries by viewModel.allEntries.collectAsState()
    
    var selectedTab by remember { mutableStateOf(0) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Habit Tracker") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            TabRow(selectedTabIndex = selectedTab) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Danas") }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Istorija") }
                )
            }
            
            when (selectedTab) {
                0 -> TodayScreen(
                    currentDate = currentDate,
                    currentEntry = currentEntry,
                    onWaterChange = viewModel::updateWater,
                    onSleepChange = viewModel::updateSleep,
                    onWalkChange = viewModel::updateWalk,
                    onPreviousDay = viewModel::goToPreviousDay,
                    onNextDay = viewModel::goToNextDay,
                    onToday = viewModel::goToToday
                )
                1 -> HistoryScreen(entries = allEntries)
            }
        }
    }
}

@Composable
fun TodayScreen(
    currentDate: java.time.LocalDate,
    currentEntry: rs.metropolitan.se330_app.data.HabitEntry?,
    onWaterChange: (Int) -> Unit,
    onSleepChange: (Float) -> Unit,
    onWalkChange: (Int) -> Unit,
    onPreviousDay: () -> Unit,
    onNextDay: () -> Unit,
    onToday: () -> Unit
) {
    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    val isToday = currentDate == java.time.LocalDate.now()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Date Navigator
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onPreviousDay) {
                    Icon(Icons.Default.ArrowBack, "Prethodni dan")
                }
                
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = currentDate.format(dateFormatter),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    if (!isToday) {
                        TextButton(onClick = onToday) {
                            Text("Idi na danas")
                        }
                    }
                }
                
                IconButton(
                    onClick = onNextDay,
                    enabled = !isToday
                ) {
                    Icon(Icons.Default.ArrowForward, "Sledeći dan")
                }
            }
        }
        
        // Habit Cards
        HabitCard(
            title = "💧 Voda",
            value = currentEntry?.waterGlasses ?: 0,
            unit = "čaša",
            icon = Icons.Default.Place,
            onIncrement = { onWaterChange((currentEntry?.waterGlasses ?: 0) + 1) },
            onDecrement = { 
                val current = currentEntry?.waterGlasses ?: 0
                if (current > 0) onWaterChange(current - 1)
            }
        )
        
        SleepCard(
            hours = currentEntry?.sleepHours ?: 0f,
            onSleepChange = onSleepChange
        )
        
        HabitCard(
            title = "🚶 Šetnja",
            value = currentEntry?.walkMinutes ?: 0,
            unit = "minuta",
            icon = Icons.Default.DateRange,
            onIncrement = { onWalkChange((currentEntry?.walkMinutes ?: 0) + 15) },
            onDecrement = { 
                val current = currentEntry?.walkMinutes ?: 0
                if (current >= 15) onWalkChange(current - 15)
            },
            step = 15
        )
    }
}

@Composable
fun HabitCard(
    title: String,
    value: Int,
    unit: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    step: Int = 1
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilledTonalButton(
                    onClick = onDecrement,
                    modifier = Modifier.size(56.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Delete, "Smanji")
                }
                
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$value",
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = unit,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                FilledTonalButton(
                    onClick = onIncrement,
                    modifier = Modifier.size(56.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Add, "Povećaj")
                }
            }
        }
    }
}

@Composable
fun SleepCard(
    hours: Float,
    onSleepChange: (Float) -> Unit
) {
    var sliderValue by remember(hours) { mutableFloatStateOf(hours) }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "😴 San",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "${String.format("%.1f", sliderValue)} sati",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Slider(
                value = sliderValue,
                onValueChange = { sliderValue = it },
                onValueChangeFinished = { onSleepChange(sliderValue) },
                valueRange = 0f..12f,
                steps = 23
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("0h", style = MaterialTheme.typography.bodySmall)
                Text("12h", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun HistoryScreen(entries: List<rs.metropolitan.se330_app.data.HabitEntry>) {
    if (entries.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Default.Face,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Nema unosa još uvek",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(entries) { entry ->
                HistoryCard(entry)
            }
        }
    }
}

@Composable
fun HistoryCard(entry: rs.metropolitan.se330_app.data.HabitEntry) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = entry.date,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("💧 Voda", style = MaterialTheme.typography.bodyMedium)
                    Text(
                        "${entry.waterGlasses} čaša",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                
                Column {
                    Text("😴 San", style = MaterialTheme.typography.bodyMedium)
                    Text(
                        "${String.format("%.1f", entry.sleepHours)} h",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                
                Column {
                    Text("🚶 Šetnja", style = MaterialTheme.typography.bodyMedium)
                    Text(
                        "${entry.walkMinutes} min",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
} 