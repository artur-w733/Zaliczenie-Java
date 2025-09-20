package com.example.spring_boot.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Encja Listing - reprezentuje ogłoszenie nieruchomości
 * 
 * Zawiera wszystkie dane potrzebne do wyświetlenia ogłoszenia:
 * - podstawowe informacje (tytuł, opis, cena)
 * - dane kontaktowe (telefon, zdjęcie)
 * - powiązanie z właścicielem (User)
 * 
 * @Entity - oznacza klasę jako encję JPA
 */
@Entity
@Table(name = "listing")
public class Listing {

    // ========== POLA KLASY ==========

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(length = 500)
    private String imageUrl;

    @Column(length = 15)
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ========== KONSTRUKTORY ==========

    public Listing() {
    }

    public Listing(String title, String description, BigDecimal price, 
                   String phoneNumber, User owner) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.phoneNumber = phoneNumber;
        this.owner = owner;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // ========== METODY CYKLU ŻYCIA JPA ==========

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // ========== GETTERY I SETTERY ==========

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // ========== METODY POMOCNICZE ==========

    public boolean hasImage() {
        return imageUrl != null && !imageUrl.trim().isEmpty();
    }

    public String getFormattedPrice() {
        if (price == null) {
            return "Cena do uzgodnienia";
        }
        return String.format("%,.2f PLN", price);
    }

    public String getShortDescription() {
        if (description == null || description.length() <= 100) {
            return description;
        }
        return description.substring(0, 100) + "...";
    }

    @Override
    public String toString() {
        return "Listing{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", owner=" + (owner != null ? owner.getUsername() : "null") +
                ", createdAt=" + createdAt +
                '}';
    }
}