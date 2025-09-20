# Portal Ogłoszeń - Spring Boot Application

Aplikacja webowa do zarządzania ogłoszeniami nieruchomości napisana w Spring Boot.

## Opis projektu

Portal Ogłoszeń to aplikacja webowa umożliwiająca:
- Przeglądanie ogłoszeń nieruchomości
- Dodawanie własnych ogłoszeń (po zalogowaniu)  
- Bezpieczne uwierzytelnianie użytkowników
- Responsywny interfejs użytkownika

## Technologie

- **Backend**: Spring Boot 3.1.5, Spring Security, Spring Data JPA
- **Frontend**: Thymeleaf, Bootstrap 5.3.2, HTML5, CSS3, JavaScript
- **Baza danych**: H2 Database (in-memory)
- **Build tool**: Maven
- **Java**: 17

## Funkcjonalności

### Dla wszystkich użytkowników:
- Przeglądanie listy ogłoszeń
- Szczegółowy widok pojedynczego ogłoszenia

### Dla zalogowanych użytkowników:
- Dodawanie nowych ogłoszeń
- Zarządzanie własnymi ogłoszeniami

## Instalacja i uruchomienie

### Wymagania:
- Java 17 lub wyższa
- Maven 3.6+

### Kroki instalacji:

1. **Rozpakuj projekt:**
   ```bash
   unzip portal-ogloszen-spring-boot.zip
   cd portal-ogloszen-spring-boot
   ```

2. **Kompilacja projektu:**
   ```bash
   mvn clean compile
   ```

3. **Uruchomienie aplikacji:**
   ```bash
   mvn spring-boot:run
   ```

4. **Dostęp do aplikacji:**
   - Aplikacja: http://localhost:8080
   - H2 Console: http://localhost:8080/h2-console

## Interfejs użytkownika

Aplikacja wykorzystuje nowoczesny design z:
- Responsywnym layoutem (mobile-first)
- Profesjonalną paletą kolorów dla branży nieruchomości
- Bootstrap 5.3.2 components
- Custom CSS z gradientami i animacjami

## Struktura projektu

```
src/
├── main/
│   ├── java/com/example/spring_boot/
│   │   ├── Application.java           # Główna klasa aplikacji
│   │   ├── config/
│   │   │   └── SecurityConfig.java    # Konfiguracja bezpieczeństwa
│   │   ├── controller/
│   │   │   ├── AuthController.java    # Logowanie/rejestracja
│   │   │   └── ListingController.java # Zarządzanie ogłoszeniami
│   │   ├── model/
│   │   │   ├── User.java              # Model użytkownika
│   │   │   └── Listing.java           # Model ogłoszenia
│   │   ├── repository/
│   │   │   ├── UserRepository.java    # Repozytorium użytkowników
│   │   │   └── ListingRepository.java # Repozytorium ogłoszeń
│   │   └── service/
│   │       ├── UserService.java       # Logika biznesowa użytkowników
│   │       └── ListingService.java    # Logika biznesowa ogłoszeń
│   └── resources/
│       ├── static/css/
│       │   └── custom-theme.css       # Niestandardowe style
│       ├── templates/
│       │   ├── login.html             # Strona logowania
│       │   ├── register.html          # Strona rejestracji
│       │   ├── listings.html          # Lista ogłoszeń
│       │   ├── addListing.html        # Dodawanie ogłoszenia
│       │   └── listingDetails.html    # Szczegóły ogłoszenia
│       └── application.properties     # Konfiguracja aplikacji
├── pom.xml                            # Konfiguracja Maven
└── README.md                          
```

## Bezpieczeństwo

- **Uwierzytelnianie**: Spring Security z BCrypt
- **CSRF Protection**: Włączona ochrona CSRF
- **Session Management**: Bezpieczne zarządzanie sesjami

## Baza danych

Aplikacja używa H2 in-memory database:
- **URL**: `jdbc:h2:mem:realtydb`
- **Username**: `sa`
- **Password**: (puste)
- **Console**: http://localhost:8080/h2-console

