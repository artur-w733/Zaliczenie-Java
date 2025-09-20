package com.example.spring_boot.controller;

import com.example.spring_boot.model.User;
import com.example.spring_boot.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Kontroler uwierzytelniania użytkowników:
 * - rejestracja
 * - logowanie
 */
@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(
            @ModelAttribute("user") User user,
            BindingResult br,
            RedirectAttributes ra,
            Model model
    ) {
        if (br.hasErrors()) {
            model.addAttribute("error", "Sprawdź poprawność wprowadzonych danych");
            return "register";
        }
        try {
            userService.register(user.getUsername(), user.getPassword());
            ra.addFlashAttribute("success", "Rejestracja zakończona pomyślnie!");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "login";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/listings";
    }
}
