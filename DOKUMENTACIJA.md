# 📱 Habit Tracker - Detalna Dokumentacija

## 📖 Sadržaj
1. [O Aplikaciji](#o-aplikaciji)
2. [Tehnologije](#tehnologije)
3. [Arhitektura](#arhitektura)
4. [Funkcionalnosti](#funkcionalnosti)
5. [Struktura Projekta](#struktura-projekta)
6. [Instalacija i Pokretanje](#instalacija-i-pokretanje)
7. [Komponente i Ekrani](#komponente-i-ekrani)
8. [Baza Podataka](#baza-podataka)
9. [Dependency Injection](#dependency-injection)
10. [Dizajn i UI/UX](#dizajn-i-uiux)

---

## 🎯 O Aplikaciji

**Habit Tracker** je moderna Android aplikacija razvijena u **Jetpack Compose** tehnologiji, koja omogućava korisnicima da prate svoje dnevne navike. Aplikacija fokusira na tri ključne navike:
- 💧 **Unos vode** (broj čaša)
- 😴 **Kvalitet sna** (broj sati)
- 🚶 **Fizička aktivnost** (minuta šetnje)

### Cilj Aplikacije
Pomoći korisnicima da grade zdrave navike kroz jednostavno praćenje, vizuelizaciju napretka i statističku analizu njihovih aktivnosti.

---

## 🛠 Tehnologije

### Core Technologies
- **Kotlin** 2.0.21 - Programski jezik
- **Jetpack Compose** - Moderni UI framework
- **Material Design 3** - Dizajn sistem
- **Android SDK 26+** - Minimalna verzija

### Architecture Components
- **Room Database** 2.6.1 - Lokalna baza podataka
- **Hilt** 2.51 - Dependency Injection
- **Coroutines & Flow** - Asinhrono programiranje
- **ViewModel** - State management
- **Navigation Compose** 2.7.7 - Navigacija između ekrana
- **DataStore** 1.0.0 - Čuvanje session podataka

### Build Tools
- **Gradle** 8.13
- **KSP** (Kotlin Symbol Processing) 2.0.21-1.0.25
- **Android Gradle Plugin** 8.13.0

---

## 🏗 Arhitektura

Aplikacija koristi **MVVM (Model-View-ViewModel)** arhitekturu sa čistim razdvajanjem odgovornosti:

```
┌─────────────────────────────────────┐
│           UI Layer                   │
│  (Composable Screens & Components)   │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│        ViewModel Layer               │
│  (Business Logic & State Management) │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│       Repository Layer               │
│    (Data Abstraction)                │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│         Data Layer                   │
│  (Room Database & DataStore)         │
└──────────────────────────────────────┘
```

### Prednosti Ove Arhitekture:
- ✅ **Testabilnost** - Svaki sloj se može testirati nezavisno
- ✅ **Održivost** - Jasna separacija odgovornosti
- ✅ **Skalabilnost** - Lako dodavanje novih funkcionalnosti
- ✅ **Reaktivnost** - Flow omogućava reaktivno programiranje

---

## 🎨 Funkcionalnosti

### 🔐 Autentifikacija
- **Registracija** korisnika sa validacijom
- **Prijava** sa čuvanjem sesije
- **Odjava** sa potvrdom

### 📊 Praćenje Navika (Glavni Ekran)
- **Dnevni unos** navika:
  - Voda: Increment/Decrement po jednoj čaši
  - San: Slider od 0 do 12 sati
  - Šetnja: Increment/Decrement po 15 minuta
- **Navigacija po datumima**:
  - Prethodni dan
  - Sledeći dan (disabled za budućnost)
  - Brzi povratak na danas
- **Animirane vrednosti** pri promeni

### 📜 Istorija
- Pregled svih prethodnih unosa
- Hronološki poredani
- Vizuelni prikaz svake navike
- Empty state za praznu listu

### 📈 Statistika (Novo!)
- **Ukupna statistika**:
  - Zbir svih čaša vode
  - Zbir svih sati sna
  - Zbir svih minuta šetnje
- **Proseci**:
  - Prosečan unos vode sa progress barom
  - Prosečan san sa progress barom
  - Prosečna šetnja sa progress barom
- **Ciljevi**:
  - Prikaz da li su ciljevi postignuti (✓ ili ✗)
  - Procenat postignuća
  - Vizuelni indikatori uspeha
- **Nedeljni pregled**:
  - Poslednja 7 unosa sa kompaktnim prikazom

### 👤 Profil
- **Korisnički avatar** (prvo slovo imena)
- **Email prikaz**
- **Informacije o nalogu**
- **Logout** sa potvrdom

---

## 📂 Struktura Projekta

```
SE330app/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/rs/metropolitan/se330_app/
│   │   │   │   ├── data/                    # Data Layer
│   │   │   │   │   ├── HabitDao.kt         # Room DAO za navike
│   │   │   │   │   ├── HabitDatabase.kt    # Room Database
│   │   │   │   │   ├── HabitEntry.kt       # Entity za navike
│   │   │   │   │   ├── HabitRepository.kt  # Repository za navike
│   │   │   │   │   ├── SessionManager.kt   # Session management
│   │   │   │   │   ├── User.kt             # Entity za korisnike
│   │   │   │   │   ├── UserDao.kt          # Room DAO za korisnike
│   │   │   │   │   └── UserRepository.kt   # Repository za korisnike
│   │   │   │   │
│   │   │   │   ├── di/                      # Dependency Injection
│   │   │   │   │   └── DatabaseModule.kt   # Hilt module
│   │   │   │   │
│   │   │   │   ├── navigation/             # Navigation
│   │   │   │   │   └── NavGraph.kt         # Navigation graph
│   │   │   │   │
│   │   │   │   ├── presentation/           # UI Layer
│   │   │   │   │   ├── auth/               # Authentication
│   │   │   │   │   │   ├── AuthViewModel.kt
│   │   │   │   │   │   ├── LoginScreen.kt
│   │   │   │   │   │   └── RegisterScreen.kt
│   │   │   │   │   │
│   │   │   │   │   ├── habit/              # Habit Tracking
│   │   │   │   │   │   ├── HabitTrackerScreen.kt
│   │   │   │   │   │   └── HabitViewModel.kt
│   │   │   │   │   │
│   │   │   │   │   ├── statistics/         # Statistics
│   │   │   │   │   │   └── StatisticsScreen.kt
│   │   │   │   │   │
│   │   │   │   │   └── profile/            # Profile
│   │   │   │   │       └── ProfileScreen.kt
│   │   │   │   │
│   │   │   │   ├── ui/theme/               # Theme & Design
│   │   │   │   │   ├── Color.kt
│   │   │   │   │   ├── Theme.kt
│   │   │   │   │   └── Type.kt
│   │   │   │   │
│   │   │   │   ├── HabitTrackerApp.kt      # Application class
│   │   │   │   └── MainActivity.kt         # Entry point
│   │   │   │
│   │   │   └── res/                        # Resources
│   │   │       ├── values/
│   │   │       │   ├── colors.xml
│   │   │       │   ├── strings.xml
│   │   │       │   └── themes.xml
│   │   │       └── ...
│   │   │
│   │   └── test/                           # Unit tests
│   │
│   └── build.gradle.kts                    # App build config
│
├── gradle/
│   └── libs.versions.toml                  # Version catalog
│
├── build.gradle.kts                        # Project build config
├── settings.gradle.kts                     # Settings
│
├── README.md                               # Osnovna dokumentacija
├── DOKUMENTACIJA.md                        # Ova datoteka
├── CHANGELOG.md                            # Lista promena
├── KORISCENJE.md                           # Uputstvo za korišćenje
├── UI_CHANGELOG.md                         # UI promene
└── UI_IMPROVEMENTS.md                      # UI poboljšanja
```

---

## 🚀 Instalacija i Pokretanje

### Preduslov
- **Android Studio** Hedgehog (2023.1.1) ili novija verzija
- **JDK** 11 ili novija verzija
- **Android SDK** 26 (minimum) - 36 (target)
- **Git**

### Koraci za Instalaciju

1. **Kloniranje repozitorijuma**
```bash
git clone <repository-url>
cd SE330app
```

2. **Otvaranje u Android Studio**
- File → Open
- Izaberite `SE330app` folder
- Sačekajte Gradle sync

3. **Pokretanje aplikacije**
- Kliknite na "Run" dugme (▶️)
- Ili: `Shift + F10`
- Ili terminal: `./gradlew installDebug`

4. **Buildovanje APK-a**
```bash
./gradlew assembleDebug
# APK će biti u: app/build/outputs/apk/debug/
```

### Troubleshooting

#### Problem: Gradle sync fails
**Rešenje:**
```bash
./gradlew clean
./gradlew build --refresh-dependencies
```

#### Problem: IDE pokazuje crvene greške ali build radi
**Rešenje:**
- File → Invalidate Caches → Invalidate and Restart

#### Problem: Room compiler greške
**Rešenje:**
- Proverite da li ste dodali `@Database` entitete
- Proverite da li su DAO interfejsi dobro definisani

---

## 🖥 Komponente i Ekrani

### 1. Login Screen (`LoginScreen.kt`)
**Komponente:**
- Email input field
- Password input field (sa show/hide opcijom)
- Login button sa loading stanjem
- Navigate to Register link
- Error handling prikaz

**ViewModel:** `AuthViewModel`

**Funkcionalnosti:**
- Validacija email i password polja
- Autentifikacija korisnika
- Čuvanje sesije nakon uspešne prijave
- Navigacija na glavni ekran

### 2. Register Screen (`RegisterScreen.kt`)
**Komponente:**
- Full name input
- Email input
- Password input
- Confirm password input
- Password match validation
- Register button sa loading stanjem

**ViewModel:** `AuthViewModel`

### 3. Habit Tracker Screen (`HabitTrackerScreen.kt`)
**Glavni kontejner sa 4 taba:**
- Danas (Today)
- Istorija (History)
- Statistika (Statistics)
- Profil (Profile)

#### Tab 1: Danas (TodayScreen)
**Komponente:**
- Date navigator card
- Water card (`ModernHabitCard`)
- Sleep card (`ModernSleepCard`)
- Walk card (`ModernHabitCard`)

**Animacije:**
- Fade in/out pri promeni vrednosti
- Slide up/down za increment/decrement
- Scale animation na button press

#### Tab 2: Istorija (HistoryScreen)
**Komponente:**
- LazyColumn sa svim unosima
- `ModernHistoryCard` za svaki unos
- Empty state view

#### Tab 3: Statistika (StatisticsScreen)
**Sekcije:**
1. **Ukupna statistika** (`OverallStatsCard`)
   - 3 stat bubbles sa ikonama
   - Ukupan zbir za svaku naviku

2. **Proseci** (`AveragesCard`)
   - Average row sa progress barom za svaku naviku
   - Vizualni indikatori napretka

3. **Ciljevi** (`GoalsCard`)
   - Goal items sa checkmark/cancel ikonama
   - Procenat postignuća
   - Color-coded success/failure

4. **Nedeljni pregled**
   - Kompaktne kartice sa poslednjim unosima
   - `WeeklyEntryCard` komponenta

#### Tab 4: Profil (ProfileScreen)
**Komponente:**
- Circular avatar
- User name display
- Email card
- Account info card
- Logout button

**ViewModel:** `AuthViewModel`

---

## 💾 Baza Podataka

### Room Database Setup

```kotlin
@Database(
    entities = [HabitEntry::class, User::class],
    version = 2,
    exportSchema = false
)
abstract class HabitDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
    abstract fun userDao(): UserDao
}
```

### Entities

#### HabitEntry
```kotlin
@Entity(tableName = "habit_entries")
data class HabitEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: String,              // Format: yyyy-MM-dd
    val waterGlasses: Int = 0,     // Broj čaša vode
    val sleepHours: Float = 0f,    // Broj sati sna
    val walkMinutes: Int = 0       // Minuta šetnje
)
```

#### User
```kotlin
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val email: String,
    val password: String,          // U produkciji: hash password!
    val fullName: String
)
```

### DAOs

#### HabitDao
**Operacije:**
- `getEntryByDate(date: String): Flow<HabitEntry?>`
- `getAllEntries(): Flow<List<HabitEntry>>`
- `insertEntry(entry: HabitEntry)`
- `updateEntry(entry: HabitEntry)`
- `deleteEntry(entry: HabitEntry)`
- `getEntriesInRange(startDate: String, endDate: String)`

#### UserDao
**Operacije:**
- `login(email: String, password: String): User?`
- `getUserByEmail(email: String): User?`
- `getUserById(userId: Long): Flow<User?>`
- `insertUser(user: User): Long`
- `updateUser(user: User)`
- `deleteUser(user: User)`

### Repository Pattern

```kotlin
// HabitRepository delegira pozive ka DAO-u
// Omogućava lako testiranje i zamenu data source-a
class HabitRepository @Inject constructor(
    private val habitDao: HabitDao
) {
    fun getEntryByDate(date: String) = habitDao.getEntryByDate(date)
    fun getAllEntries() = habitDao.getAllEntries()
    suspend fun insertOrUpdateEntry(entry: HabitEntry) { /* ... */ }
}
```

---

## 💉 Dependency Injection

Aplikacija koristi **Hilt** za dependency injection.

### DatabaseModule

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideHabitDatabase(
        @ApplicationContext context: Context
    ): HabitDatabase { /* ... */ }
    
    @Provides
    @Singleton
    fun provideHabitDao(database: HabitDatabase): HabitDao
    
    @Provides
    @Singleton
    fun provideUserDao(database: HabitDatabase): UserDao
}
```

### Kako se koristi:

#### U ViewModel:
```kotlin
@HiltViewModel
class HabitViewModel @Inject constructor(
    private val repository: HabitRepository
) : ViewModel() { /* ... */ }
```

#### U Composable:
```kotlin
@Composable
fun HabitTrackerScreen(
    viewModel: HabitViewModel = hiltViewModel()
) { /* ... */ }
```

---

## 🎨 Dizajn i UI/UX

### Color Palette

#### Primary Colors
- **Primary** - `#6366F1` (Indigo)
- **Secondary** - `#8B5CF6` (Purple)
- **Tertiary** - `#EC4899` (Pink)

#### Habit-Specific Colors
- **Water Blue** - `#3B82F6`
- **Sleep Purple** - `#8B5CF6`
- **Walk Green** - `#10B981`

#### Status Colors
- **Success** - `#10B981`
- **Error** - `#EF4444`
- **Warning** - `#F59E0B`
- **Info** - `#3B82F6`

### Typography
Material Design 3 default typography sa custom font weights.

### Dark Mode Support
✅ Podržava system dark mode sa custom dark color scheme.

### Animations
- **Fade animations** za tab promene
- **Slide animations** za broj promene
- **Scale animations** za button press feedback
- **Progress animations** za loading states

### Material Design 3 Components
- `ElevatedCard` za kartice
- `FilledButton` za primarne akcije
- `OutlinedTextField` za input polja
- `Slider` za sleep input
- `LinearProgressIndicator` za progress bars
- `AlertDialog` za potvrde

---

## 🔄 State Management

### Flow-Based Reactive Programming

```kotlin
// ViewModel emituje state kroz Flow
val currentEntry: StateFlow<HabitEntry?> = _currentDate
    .flatMapLatest { date ->
        repository.getEntryByDate(date.format(dateFormatter))
    }
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

// UI prikuplja state
val currentEntry by viewModel.currentEntry.collectAsState()
```

**Prednosti:**
- Automatsko ažuriranje UI-a
- Memory-efficient (WhileSubscribed politika)
- Type-safe

---

## 🧪 Testiranje

### Unit Tests
Lokacija: `app/src/test/`

**Šta testirati:**
- ViewModels business logiku
- Repository operacije
- Data transformacije
- Validacije

### Instrumentation Tests
Lokacija: `app/src/androidTest/`

**Šta testirati:**
- Database operacije
- UI komponente
- Navigation flow
- End-to-end scenarios

---

## 📦 Build Variants

### Debug
- Loggovanje enabled
- Debugging symbols
- Test API endpoints

### Release
- ProGuard obfuscation
- Optimizacije
- Production API endpoints

---

## 🔐 Sigurnost

### Preporuke za Produkciju:
1. **Password Hashing** - Koristiti BCrypt ili Argon2
2. **SQL Injection Protection** - Room automatski štiti
3. **Data Encryption** - Enkriptovati osetljive podatke
4. **HTTPS** - Za sve API komunikacije
5. **Token-Based Auth** - Umesto email/password

---

## 📈 Performance Optimizations

1. **Lazy Loading** - LazyColumn za liste
2. **State Hoisting** - Reusable komponente
3. **Flow** - Reaktivno ažuriranje bez polling-a
4. **Image Loading** - Coil biblioteka (ako dodajete slike)
5. **Database Indexing** - Room indeksi na često korišćenim kolonama

---

## 🌐 Internacionalizacija (i18n)

Aplikacija trenutno podržava:
- 🇷🇸 **Srpski jezik**

Za dodavanje novih jezika:
1. Kreirajte `values-<lang>/strings.xml`
2. Prevedite sve string resources
3. Test na različitim lokalizacijama

---

## 🚧 Buduća Proširenja

### Planirane Funkcionalnosti:
- [ ] Export podataka u CSV
- [ ] Grafički prikaz napretka (Charts)
- [ ] Push notifikacije za podsetnik
- [ ] Custom navike
- [ ] Social sharing
- [ ] Cloud sync
- [ ] Widget podrška
- [ ] Wear OS podrška

---

## 📞 Podrška i Kontakt

Za pitanja, bug report-e ili feature request-e:
- **Email:** [vaš-email@primer.com]
- **GitHub Issues:** [repository-url/issues]

---

## 📄 Licenca

[Stavite svoju licencu ovde]

---

## 👥 Autori

**SE330 Tim** - Metropolitan University

---

## 🙏 Zahvalnice

- Material Design 3 tim
- Jetpack Compose community
- Android Developers

---

**Poslednje ažuriranje:** 2. Oktobar 2025
**Verzija:** 1.0.0 