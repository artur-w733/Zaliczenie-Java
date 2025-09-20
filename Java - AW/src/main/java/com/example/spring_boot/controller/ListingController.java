package com.example.spring_boot.controller;

import com.example.spring_boot.model.Listing;
import com.example.spring_boot.model.User;
import com.example.spring_boot.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * Kontroler zarządzania ogłoszeniami
 * 
 * Obsługuje wszystkie operacje związane z ogłoszeniami
 */
@Controller
@RequestMapping("/listings")
public class ListingController {

    @Autowired
    private ListingService listingService;

    /**
     * Wyświetla wszystkie ogłoszenia
     */
    @GetMapping
    public String showAllListings(Model model, Principal principal) {
        try {
            List<Listing> listings = listingService.findAll();

            model.addAttribute("listings", listings);
            model.addAttribute("listingCount", listings.size());
            model.addAttribute("hasListings", !listings.isEmpty());

            if (principal != null) {
                User currentUser = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
                model.addAttribute("currentUser", currentUser);
            }

            System.out.println("Wyświetlono " + listings.size() + " ogłoszeń");

        } catch (Exception e) {
            System.err.println("Błąd podczas pobierania ogłoszeń: " + e.getMessage());
            model.addAttribute("error", "Wystąpił błąd podczas pobierania ogłoszeń.");
            model.addAttribute("listings", List.of());
        }

        return "listings";
    }

    /**
     * Wyświetla szczegóły pojedynczego ogłoszenia
     */
    @GetMapping("/{id}")
    public String showListingDetails(
            @PathVariable("id") Long id,
            Model model,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        try {
            Optional<Listing> listingOpt = listingService.findById(id);

            if (listingOpt.isPresent()) {
                Listing listing = listingOpt.get();
                model.addAttribute("listing", listing);

                if (principal != null) {
                    User currentUser = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
                    boolean isOwner = listing.getOwner().getId().equals(currentUser.getId());
                    model.addAttribute("isOwner", isOwner);
                    model.addAttribute("currentUser", currentUser);
                }

                return "listingDetails";

            } else {
                redirectAttributes.addFlashAttribute("error", 
                    "Ogłoszenie o podanym ID nie zostało znalezione.");
                return "redirect:/listings";
            }

        } catch (Exception e) {
            System.err.println("Błąd podczas pobierania szczegółów ogłoszenia: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", 
                "Wystąpił błąd podczas pobierania szczegółów ogłoszenia.");
            return "redirect:/listings";
        }
    }

    /**
     * Wyświetla formularz dodawania nowego ogłoszenia
     */
    @GetMapping("/add")
    public String showAddListingForm(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        model.addAttribute("listing", new Listing());

        User currentUser = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        model.addAttribute("currentUser", currentUser);

        return "addListing";
    }

    /**
     * Przetwarza dane z formularza dodawania ogłoszenia
     */
    @PostMapping("/add")
    public String processAddListing(
            @ModelAttribute("listing") Listing listing,
            BindingResult bindingResult,
            Principal principal,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (principal == null) {
            return "redirect:/login";
        }

        try {
            User currentUser = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
            listing.setOwner(currentUser);

            if (bindingResult.hasErrors()) {
                model.addAttribute("error", "Sprawdź poprawność wprowadzonych danych");
                model.addAttribute("currentUser", currentUser);
                return "addListing";
            }

            Listing savedListing = listingService.save(listing);

            redirectAttributes.addFlashAttribute("success", 
                "Ogłoszenie zostało pomyślnie dodane!");

            return "redirect:/listings/" + savedListing.getId();

        } catch (Exception e) {
            model.addAttribute("error", 
                "Wystąpił błąd podczas dodawania ogłoszenia.");
            model.addAttribute("listing", listing);

            User currentUser = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
            model.addAttribute("currentUser", currentUser);

            return "addListing";
        }
    }
}