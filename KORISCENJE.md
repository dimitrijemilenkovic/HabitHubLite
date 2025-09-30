# Vodič za Korišćenje - Habit Tracker

## 🎯 Struktura Projekta

```
app/src/main/java/rs/metropolitan/se330_app/
│
├── data/                          # Data Layer
│   ├── HabitEntry.kt             # Room Entity
│   ├── HabitDao.kt               # Database DAO
│   ├── HabitDatabase.kt          # Room Database
│   └── HabitRepository.kt        # Repository pattern
│
├── di/                           # Dependency Injection
│   └── DatabaseModule.kt         # Hilt module
│
├── presentation/                 # UI Layer
│   ├── HabitViewModel.kt        # ViewModel sa business logikom
│   └── HabitTrackerScreen.kt    # Compose UI komponente
│
├── ui/theme/                     # Design system
│   ├── Color.kt
│   ├── Theme.kt
│   └── Type.kt
│
├── MainActivity.kt               # Entry point
└── HabitTrackerApp.kt           # Application class sa @HiltAndroidApp
```

## 📱 Kako Koristiti Aplikaciju

### Glavni Ekran - "Danas"

1. **Navigacija po datumima**
   - Strelica levo: idi na prethodni dan
   - Strelica desno: idi na sledeći dan (disabled za buduće dane)
   - Dugme "Idi na danas": vraća se na današnji dan

2. **Unos Vode 💧**
   - Klikni `+` da dodaš jednu čašu vode
   - Klikni `-` da ukloniš jednu čašu
   - Brojač pokazuje ukupan broj čaša

3. **Unos Sna 😴**
   - Koristi slider za podešavanje sati sna (0-12 sati)
   - Preciznost: 0.5 sati (pola sata)
   - Automatski se čuva kada pustiš slider

4. **Unos Šetnje 🚶**
   - Klikni `+` da dodaš 15 minuta
   - Klikni `-` da ukloniš 15 minuta
   - Koriste se 15-minutni intervali

### Ekran Istorije

- Prikazuje sve prethodne unose
- Sortiran od najnovijih ka najstarijim
- Svaka kartica pokazuje datum i sve tri navike
- Ako nema unosa, prikazuje prazan state

## 💾 Kako Radi Čuvanje Podataka

- **Automatsko čuvanje**: Svaki unos se automatski čuva u Room bazu
- **Per-datum baza**: Svaki datum ima svoj zapis
- **Asinhrono**: Sve operacije sa bazom se izvršavaju van glavnog thread-a
- **Reaktivno**: UI se automatski ažurira kada se promene podaci

## 🔧 Ključne Komponente

### 1. Room Database
```kotlin
@Entity(tableName = "habit_entries")
data class HabitEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: String,
    val waterGlasses: Int = 0,
    val sleepHours: Float = 0f,
    val walkMinutes: Int = 0
)
```

### 2. Hilt Dependency Injection
- `@HiltAndroidApp` na Application class
- `@AndroidEntryPoint` na MainActivity
- `@HiltViewModel` na ViewModel
- `@InstallIn(SingletonComponent::class)` na Module

### 3. Reactive Programming
- `StateFlow` za state management
- `Flow` za database queries
- `viewModelScope` za coroutine lifecycle

## 🎨 UI Design Patterns

### Material 3 Komponente Korišćene:
- `TopAppBar` - App bar sa naslovom
- `TabRow` i `Tab` - Navigacija između ekrana
- `Card` - Kartice za svaku naviku
- `Slider` - Unos sati sna
- `FilledTonalButton` - Dugmad za increment/decrement
- `LazyColumn` - Lista istorije (scrollable)

### Color Scheme:
- `primaryContainer` - Headeri
- `secondaryContainer` - Date navigator
- `primary` - Brojevi i vrednosti
- `onSurfaceVariant` - Sekundarni tekst

## 🚀 Build i Run

1. **Sync Gradle**
   ```
   Gradle sync će preuzeti sve zavisnosti:
   - Hilt
   - Room
   - Compose dependencies
   ```

2. **Build APK**
   ```
   Build -> Build Bundle(s) / APK(s) -> Build APK(s)
   ```

3. **Run na uređaju**
   ```
   Run -> Run 'app'
   ```

## 🧪 Testing Points

### Šta testirati:
1. ✅ Kreiranje novog unosa za današnji dan
2. ✅ Ažuriranje postojećeg unosa
3. ✅ Navigacija između datuma
4. ✅ Prikaz istorije
5. ✅ Perzistencija podataka (restart app)

## 🔍 Debugging

### Logcat Filter:
```
package:rs.metropolitan.se330_app
```

### Database Inspector:
```
View -> Tool Windows -> App Inspection -> Database Inspector
```

### Compose Layout Inspector:
```
Tools -> Layout Inspector
```

## 📝 Notes

- minSdk = 26 (Android 8.0 Oreo)
- Koristi Java 8 Time API (`LocalDate`, `DateTimeFormatter`)
- Svi string-ovi su na srpskom jeziku
- UI je responsive i radi na svim veličinama ekrana
- Dark mode automatski podržan preko Material 3 theme 