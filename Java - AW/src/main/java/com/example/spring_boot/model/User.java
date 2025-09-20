package com.example.spring_boot.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Encja User - reprezentuje użytkownika systemu
 * 
 * Implementuje UserDetails dla integracji z Spring Security
 * Zawiera podstawowe dane do uwierzytelniania i autoryzacji
 * 
 * @Entity - oznacza klasę jako encję JPA
 * @Table - definiuje nazwę tabeli w bazie danych
 */
@Entity
@Table(name = "app_user") // Nazwa "user" jest zarezerwowana w niektórych bazach danych
public class User implements UserDetails {

    // ========== POLA KLASY ==========

    /**
     * Identyfikator użytkownika (klucz główny)
     * Auto-generowany przez bazę danych
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nazwa użytkownika - unikalna i wymagana
     * Używana do logowania
     */
    @Column(unique = true, nullable = false)
    private String username;

    /**
     * Hasło użytkownika - wymagane, szyfrowane
     * Przechowywane jako hash BCrypt
     */
    @Column(nullable = false)
    private String password;

    // ========== KONSTRUKTORY ==========

    /**
     * Konstruktor domyślny - wymagany przez JPA
     */
    public User() {
    }

    /**
     * Konstruktor z parametrami - do łatwego tworzenia użytkowników
     * 
     * @param username nazwa użytkownika
     * @param password hasło (będzie zaszyfrowane w serwisie)
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // ========== IMPLEMENTACJA UserDetails ==========

    /**
     * Zwraca role użytkownika
     * Obecnie wszyscy użytkownicy mają takie same uprawnienia
     * 
     * @return kolekcja uprawnień (obecnie pusta)
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // Brak specjalnych ról - wszyscy użytkownicy równorzędni
    }

    /**
     * Sprawdza czy konto nie wygasło
     * 
     * @return true - konta nie wygasają
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Sprawdza czy konto nie jest zablokowane
     * 
     * @return true - nie ma blokowania kont
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Sprawdza czy dane uwierzytelniające nie wygasły
     * 
     * @return true - dane nie wygasają
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Sprawdza czy konto jest aktywne
     * 
     * @return true - wszystkie konta są aktywne
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    // ========== GETTERY I SETTERY ==========

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}