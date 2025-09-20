package com.example.spring_boot.repository;

import com.example.spring_boot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repozytorium dla encji User
 * 
 * Zapewnia dostęp do danych użytkowników w bazie danych
 * Rozszerza JpaRepository co daje podstawowe operacje CRUD
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Znajduje użytkownika po nazwie użytkownika
     * 
     * @param username nazwa użytkownika do wyszukania
     * @return Optional z użytkownikiem lub pusty jeśli nie znaleziono
     */
    Optional<User> findByUsername(String username);

    /**
     * Sprawdza czy użytkownik o podanej nazwie już istnieje
     * 
     * @param username nazwa użytkownika do sprawdzenia
     * @return true jeśli użytkownik istnieje, false w przeciwnym razie
     */
    boolean existsByUsername(String username);
}