# Habit Tracker App

MVP aplikacija za praćenje dnevnih navika izrađena u Kotlin-u sa Jetpack Compose, **sa kompletnom autentifikacijom korisnika**.

## 📱 Funkcionalnosti

### 🔐 Autentifikacija
- **Registracija** - Kreiranje novog naloga sa validacijom
- **Login** - Prijava sa email-om i password-om
- **Logout** - Odjava sa potvrdom
- **Session Management** - Automatsko pamćenje ulogovanog korisnika

### 📊 Praćenje Navika
Aplikacija prati tri osnovne navike tokom dana:
- 💧 **Unos vode** - Praćenje broja popijenih čaša vode
- 😴 **San** - Evidentiranje sati spavanja (0-12 sati)
- 🚶 **Šetnja** - Praćenje minuta šetnje

## 🏗️ Arhitektura

Projekat prati **MVP (Model-View-Presenter)** arhitekturu sa sledećom strukturom:

### Data Layer
```
data/
├── User.kt                - Room Entity za korisnike
├── UserDao.kt             - DAO za user operacije
├── UserRepository.kt      - Repository za user data
├── HabitEntry.kt          - Room Entity za unose navika
├── HabitDao.kt            - DAO interfejs za database operacije
├── HabitDatabase.kt       - Room Database (users + habit_entries)
├── HabitRepository.kt     - Repository za pristup podacima
└── SessionManager.kt      - DataStore za session management
```

### DI Layer (Dependency Injection)
```
di/
└── DatabaseModule.kt      - Hilt module za dependency injection
```

### Presentation Layer
```
presentation/
├── auth/
│   ├── AuthViewModel.kt       - ViewModel za autentifikaciju
│   ├── LoginScreen.kt         - Login UI
│   └── RegisterScreen.kt      - Registracija UI
├── habit/
│   ├── HabitViewModel.kt      - ViewModel za business logiku navika
│   └── HabitTrackerScreen.kt  - Main UI sa tabovima (Danas, Istorija, Stats, Profil)
├── statistics/
│   └── StatisticsScreen.kt    - Statistika i analiza navika
└── profile/
    └── ProfileScreen.kt       - Korisnički profil
```

### Navigation Layer
```
navigation/
└── NavGraph.kt            - Jetpack Navigation sistem
```

## 🛠️ Tehnologije

- **Kotlin** - Programski jezik
- **Jetpack Compose** - Moderni UI toolkit
- **Room Database** - Lokalna baza podataka (Users + Habit Entries)
- **Hilt** - Dependency Injection framework
- **Navigation Compose** - Navigacija između ekrana
- **DataStore** - Preferences za session management
- **Coroutines & Flow** - Asinhrono programiranje i reaktivni tokovi
- **Material 3** - Dizajn sistem

## 📊 Struktura Baze Podataka

### User Entity
| Polje     | Tip    | Opis                           |
|-----------|--------|--------------------------------|
| id        | Long   | Primarni ključ (auto-generate) |
| email     | String | Email (unique index)           |
| password  | String | Password (plain text - dev)    |
| fullName  | String | Puno ime korisnika            |
| createdAt | String | Datum registracije            |

### HabitEntry Entity
| Polje        | Tip    | Opis                          |
|--------------|--------|-------------------------------|
| id           | Long   | Primarni ključ (auto-generate)|
| date         | String | Datum (format: yyyy-MM-dd)    |
| waterGlasses | Int    | Broj čaša vode                |
| sleepHours   | Float  | Sati spavanja                 |
| walkMinutes  | Int    | Minuti šetnje                 |

## 🎨 UI Komponente

### Login Ekran
- Email i Password input polja
- Validacija unosa
- Loading state
- Link za registraciju
- Error handling

### Register Ekran
- Puno ime, Email, Password, Potvrda password-a
- Real-time validacija
- Password match provera
- Email format validacija
- Link za login

### Glavni Ekran (Danas) - Zaštićen autentifikacijom
- Logout dugme u TopAppBar
- Navigator za navigaciju između datuma
- Card za unos vode (+/- dugmad)
- Slider za unos sati spavanja
- Card za unos minuta šetnje

### Ekran Istorije
- Lista prethodnih unosa
- Prikaz svih navika po datumima
- Prazan state kada nema podataka

### Statistika Ekran - NOVO! 📊
- **Ukupna statistika** - Zbir svih unosa za svaku naviku
- **Proseci** - Prosečne vrednosti sa progress barovima
- **Ciljevi** - Prikaz postignuća sa procentima
- **Nedeljni pregled** - Kompaktan prikaz poslednjih 7 dana

### Profil Ekran - NOVO! 👤
- Kružni avatar sa inicijalima
- Email i korisničke informacije
- Logout funkcionalnost

## 🔐 Session Management

- **DataStore Preferences** za čuvanje user ID-a
- **Automatski login** ako je sesija aktivna
- **Logout** čisti sesiju i vraća na login
- **Navigation protection** - zaštita ruta

## 🚀 Kako pokrenuti projekat

1. Otvorite projekat u Android Studio
2. Sačekajte da se Gradle sinhronizuje
3. Pokrenite aplikaciju na emulatoru ili fizičkom uređaju
4. **Registrujte se** ili se **prijavite**
5. Počnite sa praćenjem svojih navika!

## 📝 Zavisnosti

Sve zavisnosti su definisane u `libs.versions.toml`:
- Room: 2.6.1
- Hilt: 2.51
- Navigation Compose: 2.7.7
- DataStore: 1.0.0
- Compose BOM: 2024.09.00
- KSP: 2.0.21-1.0.25

## 🔧 Gradle Build

Projekat koristi:
- Kotlin 2.0.21
- Android Gradle Plugin 8.13.0
- minSdk: 26
- targetSdk: 36
- compileSdk: 36






## 📁 Struktura Projekta

```
SE330app/
├── app/src/main/java/rs/metropolitan/se330_app/
│   ├── data/              # Data Layer (Room Database, Repositories)
│   ├── di/                # Dependency Injection (Hilt modules)
│   ├── navigation/        # Navigation Graph
│   ├── presentation/      # UI Layer
│   │   ├── auth/          # Authentication screens
│   │   ├── habit/         # Habit tracking screens
│   │   ├── statistics/    # Statistics screen
│   │   └── profile/       # Profile screen
│   └── ui/theme/          # Theme & Design System
└── docs/                  # Dokumentacija fajlovi
``` 
