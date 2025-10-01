# Changelog

## [Version 2.0] - Authentication Update - 2025-10-01

### ✨ Nove Funkcionalnosti

#### 🔐 Autentifikacija Sistema
- **User Registration** - Kompletan sistem registracije sa validacijom
  - Email validacija (format check)
  - Password validacija (minimum 6 karaktera)
  - Password confirmation
  - Unique email constraint
  - Full name support
  
- **User Login** - Sistem prijave
  - Email/Password autentifikacija
  - Error handling za pogrešne kredencijale
  - Loading states
  
- **Session Management** - Upravljanje sesijom
  - DataStore Preferences za perzistenciju
  - Automatski login nakon restarta
  - Secure logout sa confirmation
  
- **Protected Navigation** - Zaštićene rute
  - Automatski redirect na login ako nije ulogovan
  - Navigation graph sa protected routes

#### 🗄️ Database Proširenje
- **Users Table** - Nova tabela za korisnike
  - Unique email index
  - User profile data (full name, created date)
  - Auto-generated ID
  
- **Database Version 2** - Nadogradnja
  - Migration strategy (fallbackToDestructiveMigration za dev)
  - Multi-table support

#### 🎨 UI Komponente
- **LoginScreen** - Login forma
  - Material 3 design
  - Password visibility toggle
  - Error display
  - Link ka registraciji
  
- **RegisterScreen** - Registraciona forma
  - 4 input polja (name, email, password, confirm)
  - Real-time validacija
  - Password match indicator
  - Scrollable layout
  
- **Logout Dialog** - Confirmation dialog
  - User-friendly odjava
  - Cancel opcija

#### 🔧 Technical Additions
- **Navigation Compose** - Jetpack Navigation
  - NavHost setup
  - Screen routes
  - Navigation callbacks
  
- **DataStore** - Preferences storage
  - User session persistence
  - Flow-based reactive storage
  
- **AuthViewModel** - Authentication state management
  - Login/Register logic
  - State management (Idle, Loading, Success, Error)
  - Session handling
  
- **UserRepository & UserDao** - Data layer za korisnike
  - CRUD operations
  - Login/Register business logic
  - Email uniqueness check

### 🔄 Izmene

#### Ažurirani Fajlovi
- `MainActivity.kt` - Sada koristi AppNavGraph umesto direktnog HabitTrackerScreen
- `HabitTrackerScreen.kt` - Dodat logout dugme i logout callback
- `HabitDatabase.kt` - Proširena sa Users table, verzija 2
- `DatabaseModule.kt` - Dodat UserDao provider
- `README.md` - Ažurirana dokumentacija sa auth features
- `KORISCENJE.md` - Uprosećen sa auth instrukcijama

#### Nove Dependencies
```toml
navigationCompose = "2.7.7"
datastore = "1.0.0"
```

### 📁 Nova Struktura Fajlova

```
app/src/main/java/rs/metropolitan/se330_app/
├── data/
│   ├── User.kt                    [NOVO]
│   ├── UserDao.kt                 [NOVO]
│   ├── UserRepository.kt          [NOVO]
│   └── SessionManager.kt          [NOVO]
│
├── presentation/
│   └── auth/                      [NOVO]
│       ├── AuthViewModel.kt       [NOVO]
│       ├── LoginScreen.kt         [NOVO]
│       └── RegisterScreen.kt      [NOVO]
│
└── navigation/                    [NOVO]
    └── NavGraph.kt                [NOVO]
```

### 📊 Statistika

- **Novi fajlovi**: 8
- **Ažurirani fajlovi**: 6
- **Nove dependencies**: 2
- **Database verzija**: 1 → 2
- **Ukupno linija koda dodato**: ~1,000+

### 🔒 Security Notes

⚠️ **Development Environment:**
- Password-i se čuvaju kao plain text
- Nema encryption layer
- Za produkciju potrebno:
  - Password hashing (bcrypt/argon2)
  - Encrypted DataStore
  - JWT tokens
  - SSL pinning

### 🧪 Testing Checklist

- [x] Registracija novog korisnika
- [x] Login sa validnim kredencijalima
- [x] Login sa invalidnim kredencijalima
- [x] Email format validacija
- [x] Password length validacija
- [x] Password match validacija
- [x] Session persistence
- [x] Automatski login
- [x] Logout funkcionalnost
- [x] Navigation flow
- [x] Database migrations

### 📝 Notes

- Arhitektura ostala ista (MVP pattern)
- Hilt DI korišćen za sve nove komponente
- Material 3 design language konzistentan
- Svi stringovi na srpskom jeziku
- Reactive programming sa Flow i StateFlow

---

## [Version 1.0] - Initial Release - 2025-10-01

### 🎯 Osnovne Funkcionalnosti

- Habit tracking za vodu, san, šetnju
- Room Database integration
- Hilt Dependency Injection
- Material 3 UI
- Date navigation
- History screen
- Compose UI sa reactive updates 