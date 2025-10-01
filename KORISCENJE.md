# Vodič za Korišćenje - Habit Tracker sa Autentifikacijom

## 🔐 Autentifikacija - Prvi Koraci

### Registracija Novog Korisnika
1. Pokrenite aplikaciju
2. Kliknite **"Registrujte se"** na Login ekranu
3. Popunite formu:
   - **Puno ime**: Vaše ime i prezime
   - **Email**: Validna email adresa
   - **Password**: Minimum 6 karaktera
   - **Potvrdite Password**: Mora se poklapati
4. Kliknite **"Registruj se"**
5. Automatski ulazite u aplikaciju

### Login
1. Unesite email i password
2. Kliknite **"Prijavi se"**
3. Ako su podaci ispravni, ulazite u aplikaciju

### Logout
1. Kliknite ikonu Exit u gornjem desnom uglu
2. Potvrdite odjavu
3. Vraćate se na Login ekran

## 📱 Praćenje Navika

### Unos Vode 💧
- Kliknite `+` za dodavanje čaše
- Kliknite `-` za uklanjanje čaše

### Unos Sna 😴
- Pomerajte slider (0-12 sati)
- Automatsko čuvanje

### Šetnja 🚶
- Dodajte/uklonite po 15 minuta
- Kliknite `+` ili `-`

## 🔒 Bezbednost

⚠️ **Napomena**: Password-i se čuvaju kao plain text. Za produkciju koristiti hash-ovanje!

## 📊 Struktura

- **2 tabele**: Users i Habit Entries
- **Session**: DataStore Preferences
- **Navigation**: Protected routes
- **Multi-user**: Svaki korisnik ima svoje podatke 