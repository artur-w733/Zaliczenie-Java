package com.example.spring_boot.service;

import com.example.spring_boot.model.Listing;
import com.example.spring_boot.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Serwis do zarządzania ogłoszeniami
 * 
 * Warstwa biznesowa odpowiedzialna za:
 * - operacje CRUD na ogłoszeniach
 * - walidację danych biznesowych
 */
@Service
@Transactional
public class ListingService {

    @Autowired
    private ListingRepository listingRepository;

    /**
     * Zapisuje nowe ogłoszenie lub aktualizuje istniejące
     * 
     * @param listing ogłoszenie do zapisania
     * @return zapisane ogłoszenie z przypisanym ID
     */
    public Listing save(Listing listing) {
        System.out.println("Zapisywanie ogłoszenia: " + listing.getTitle());
        return listingRepository.save(listing);
    }

    /**
     * Pobiera wszystkie ogłoszenia posortowane po dacie
     * 
     * @return lista ogłoszeń sortowana po dacie utworzenia malejąco
     */
    @Transactional(readOnly = true)
    public List<Listing> findAll() {
        return listingRepository.findAllByOrderByCreatedAtDesc();
    }

    /**
     * Znajduje ogłoszenie po ID
     * 
     * @param id identyfikator ogłoszenia
     * @return Optional z ogłoszeniem lub pusty jeśli nie znaleziono
     */
    @Transactional(readOnly = true)
    public Optional<Listing> findById(Long id) {
        return listingRepository.findById(id);
    }

    /**
     * Znajduje ogłoszenia konkretnego użytkownika
     * 
     * @param ownerId ID właściciela ogłoszeń
     * @return lista ogłoszeń użytkownika
     */
    @Transactional(readOnly = true)
    public List<Listing> findByOwner(Long ownerId) {
        return listingRepository.findAllByOwnerId(ownerId);
    }
}