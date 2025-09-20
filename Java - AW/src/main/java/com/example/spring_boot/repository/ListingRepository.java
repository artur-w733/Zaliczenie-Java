package com.example.spring_boot.repository;

import com.example.spring_boot.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repozytorium dla encji Listing
 * 
 * Zapewnia dostęp do danych ogłoszeń w bazie danych
 * Zawiera metody do wyszukiwania, filtrowania i sortowania ogłoszeń
 */
@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {

    /**
     * Znajduje wszystkie ogłoszenia konkretnego użytkownika
     * 
     * @param ownerId ID właściciela ogłoszeń
     * @return lista ogłoszeń należących do użytkownika
     */
    List<Listing> findAllByOwnerId(Long ownerId);

    /**
     * Znajduje wszystkie ogłoszenia posortowane po dacie utworzenia (najnowsze pierwsze)
     * 
     * @return lista ogłoszeń sortowana od najnowszych
     */
    List<Listing> findAllByOrderByCreatedAtDesc();
}