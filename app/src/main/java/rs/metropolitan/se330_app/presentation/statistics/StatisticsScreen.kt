package rs.metropolitan.se330_app.presentation.statistics

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.automirrored.outlined.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import rs.metropolitan.se330_app.data.HabitEntry
import rs.metropolitan.se330_app.presentation.habit.HabitViewModel
import rs.metropolitan.se330_app.ui.theme.*
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
    viewModel: HabitViewModel = hiltViewModel()
) {
    val allEntries by viewModel.allEntries.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            Icons.Filled.BarChart,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text("Statistika", fontWeight = FontWeight.Bold)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { padding ->
        if (allEntries.isEmpty()) {
            EmptyStatisticsView(Modifier.padding(padding))
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(MaterialTheme.colorScheme.background),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        "Vaša statistika",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                
                item {
                    OverallStatsCard(allEntries)
                }
                
                item {
                    Text(
                        "Proseci",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                
                item {
                    AveragesCard(allEntries)
                }
                
                item {
                    Text(
                        "Ciljevi",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                
                item {
                    GoalsCard(allEntries)
                }
                
                item {
                    Text(
                        "Nedeljni pregled",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                
                items(allEntries.take(7)) { entry ->
                    WeeklyEntryCard(entry)
                }
            }
        }
    }
}

@Composable
fun EmptyStatisticsView(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                Icons.AutoMirrored.Outlined.ShowChart,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            )
            Text(
                "Nema podataka za statistiku",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                "Dodajte svoje navike da vidite statistiku",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun OverallStatsCard(entries: List<HabitEntry>) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                "Ukupno",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatBubble(
                    value = entries.sumOf { it.waterGlasses },
                    label = "Čaša vode",
                    color = WaterBlue,
                    icon = Icons.Outlined.WaterDrop
                )
                
                StatBubble(
                    value = entries.sumOf { it.sleepHours.toInt() },
                    label = "Sati sna",
                    color = SleepPurple,
                    icon = Icons.Outlined.Bedtime
                )
                
                StatBubble(
                    value = entries.sumOf { it.walkMinutes },
                    label = "Min šetnje",
                    color = WalkGreen,
                    icon = Icons.AutoMirrored.Outlined.DirectionsWalk
                )
            }
        }
    }
}

@Composable
fun StatBubble(
    value: Int,
    label: String,
    color: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$value",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = color
                )
            }
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun AveragesCard(entries: List<HabitEntry>) {
    val avgWater = entries.map { it.waterGlasses }.average()
    val avgSleep = entries.map { it.sleepHours.toDouble() }.average()
    val avgWalk = entries.map { it.walkMinutes }.average()
    
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AverageRow(
                label = "Prosek vode",
                value = String.format("%.1f čaša", avgWater),
                icon = Icons.Outlined.WaterDrop,
                color = WaterBlue,
                progress = (avgWater / 8).toFloat().coerceIn(0f, 1f)
            )
            
            AverageRow(
                label = "Prosek sna",
                value = String.format("%.1f sati", avgSleep),
                icon = Icons.Outlined.Bedtime,
                color = SleepPurple,
                progress = (avgSleep / 8).toFloat().coerceIn(0f, 1f)
            )
            
            AverageRow(
                label = "Prosek šetnje",
                value = "${avgWalk.roundToInt()} min",
                icon = Icons.AutoMirrored.Outlined.DirectionsWalk,
                color = WalkGreen,
                progress = (avgWalk / 60).toFloat().coerceIn(0f, 1f)
            )
        }
    }
}

@Composable
fun AverageRow(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    progress: Float
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
        
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = color,
            trackColor = color.copy(alpha = 0.2f),
        )
    }
}

@Composable
fun GoalsCard(entries: List<HabitEntry>) {
    val waterGoal = 8
    val sleepGoal = 8
    val walkGoal = 30
    
    val avgWater = entries.map { it.waterGlasses }.average()
    val avgSleep = entries.map { it.sleepHours.toDouble() }.average()
    val avgWalk = entries.map { it.walkMinutes }.average()
    
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "Dostignuti ciljevi",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            
            GoalItem(
                label = "Voda (cilj: $waterGoal čaša)",
                achieved = avgWater >= waterGoal,
                percentage = ((avgWater / waterGoal) * 100).roundToInt()
            )
            
            GoalItem(
                label = "San (cilj: $sleepGoal sati)",
                achieved = avgSleep >= sleepGoal,
                percentage = ((avgSleep / sleepGoal) * 100).roundToInt()
            )
            
            GoalItem(
                label = "Šetnja (cilj: $walkGoal min)",
                achieved = avgWalk >= walkGoal,
                percentage = ((avgWalk / walkGoal) * 100).roundToInt()
            )
        }
    }
}

@Composable
fun GoalItem(
    label: String,
    achieved: Boolean,
    percentage: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                if (achieved) Icons.Filled.CheckCircle else Icons.Outlined.Cancel,
                contentDescription = null,
                tint = if (achieved) Success else Error,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        Text(
            text = "$percentage%",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = if (achieved) Success else MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
fun WeeklyEntryCard(entry: HabitEntry) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = entry.date,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CompactStat(
                        icon = Icons.Outlined.WaterDrop,
                        value = "${entry.waterGlasses}",
                        color = WaterBlue
                    )
                    CompactStat(
                        icon = Icons.Outlined.Bedtime,
                        value = String.format("%.1f", entry.sleepHours),
                        color = SleepPurple
                    )
                    CompactStat(
                        icon = Icons.AutoMirrored.Outlined.DirectionsWalk,
                        value = "${entry.walkMinutes}",
                        color = WalkGreen
                    )
                }
            }
            
            Icon(
                Icons.AutoMirrored.Filled.TrendingUp,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun CompactStat(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    value: String,
    color: Color
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Medium,
            color = color
        )
    }
} 