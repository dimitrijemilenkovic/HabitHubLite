# Habit Tracker App

MVP aplikacija za praćenje dnevnih navika izrađena u Kotlin-u sa Jetpack Compose.

## 📱 Funkcionalnosti

Aplikacija prati tri osnovne navike tokom dana:
- 💧 **Unos vode** - Praćenje broja popijenih čaša vode
- 😴 **San** - Evidentiranje sati spavanja (0-12 sati)
- 🚶 **Šetnja** - Praćenje minuta šetnje

## 🏗️ Arhitektura

Projekat prati **MVP (Model-View-Presenter)** arhitekturu sa sledećom strukturom:

### Data Layer
```
data/
├── HabitEntry.kt       - Room Entity za unose navika
├── HabitDao.kt         - DAO interfejs za database operacije
├── HabitDatabase.kt    - Room Database
└── HabitRepository.kt  - Repository za pristup podacima
```

### DI Layer (Dependency Injection)
```
di/
└── DatabaseModule.kt   - Hilt module za dependency injection
```

### Presentation Layer
```
presentation/
├── HabitViewModel.kt      - ViewModel za business logiku
└── HabitTrackerScreen.kt  - Compose UI komponente
```

## 🛠️ Tehnologije

- **Kotlin** - Programski jezik
- **Jetpack Compose** - Moderni UI toolkit
- **Room Database** - Lokalna baza podataka
- **Hilt** - Dependency Injection framework
- **Coroutines & Flow** - Asinhrono programiranje i reaktivni tokovi
- **Material 3** - Dizajn sistem

## 📊 Struktura Baze Podataka

### HabitEntry Entity
| Polje        | Tip    | Opis                          |
|--------------|--------|-------------------------------|
| id           | Long   | Primarni ključ (auto-generate)|
| date         | String | Datum (format: yyyy-MM-dd)    |
| waterGlasses | Int    | Broj čaša vode                |
| sleepHours   | Float  | Sati spavanja                 |
| walkMinutes  | Int    | Minuti šetnje                 |

## 🎨 UI Komponente

### Glavni Ekran (Danas)
- Navigator za navigaciju između datuma
- Card za unos vode (+/- dugmad)
- Slider za unos sati spavanja
- Card za unos minuta šetnje

### Ekran Istorije
- Lista prethodnih unosa
- Prikaz svih navika po datumima
- Prazan state kada nema podataka

## 🚀 Kako pokrenuti projekat

1. Otvorite projekat u Android Studio
2. Sačekajte da se Gradle sinhronizuje
3. Pokrenite aplikaciju na emulatoru ili fizičkom uređaju

## 📝 Zavisnosti

Sve zavisnosti su definisane u `libs.versions.toml`:
- Room: 2.6.1
- Hilt: 2.51
- Compose BOM: 2024.09.00
- KSP: 2.0.21-1.0.25

## 🔧 Gradle Build

Projekat koristi:
- Kotlin 2.0.21
- Android Gradle Plugin 8.13.0
- minSdk: 26
- targetSdk: 36
- compileSdk: 36

## 💡 Dodatne Mogućnosti za Proširenje

- Statistike i grafikoni
- Notifikacije za podsetnik
- Ciljevi i achievements
- Export podataka
- Dark mode
- Widget za home screen 