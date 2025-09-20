package com.example.spring_boot.service;

import com.example.spring_boot.model.User;
import com.example.spring_boot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serwis do zarządzania użytkownikami
 * 
 * Implementuje UserDetailsService dla integracji z Spring Security
 * Obsługuje rejestrację, uwierzytelnianie i podstawowe operacje na użytkownikach
 */
@Service
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Ładuje użytkownika po nazwie dla Spring Security
     * 
     * @param username nazwa użytkownika
     * @return szczegóły użytkownika (UserDetails)
     * @throws UsernameNotFoundException gdy użytkownik nie istnieje
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                    "Nie znaleziono użytkownika o nazwie: " + username));
    }

    /**
     * Rejestruje nowego użytkownika w systemie
     * 
     * @param username nazwa użytkownika (musi być unikalna)
     * @param rawPassword hasło w postaci zwykłego tekstu
     * @return zapisany użytkownik
     */
    public User register(String username, String rawPassword) {
        // Sprawdzenie czy użytkownik już istnieje
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException(
                "Użytkownik o nazwie '" + username + "' już istnieje");
        }

        // Utworzenie nowego użytkownika
        User newUser = new User();
        newUser.setUsername(username.trim());
        newUser.setPassword(passwordEncoder.encode(rawPassword));

        // Zapis do bazy danych
        User savedUser = userRepository.save(newUser);

        System.out.println("Zarejestrowano nowego użytkownika: " + username);
        return savedUser;
    }

    /**
     * Pobiera liczbę zarejestrowanych użytkowników
     * 
     * @return liczba użytkowników w systemie
     */
    @Transactional(readOnly = true)
    public long getUserCount() {
        return userRepository.count();
    }

    /**
     * Sprawdza czy system ma już jakichkolwiek użytkowników
     * 
     * @return true jeśli są użytkownicy, false jeśli system jest pusty
     */
    @Transactional(readOnly = true)
    public boolean hasUsers() {
        return userRepository.count() > 0;
    }
}