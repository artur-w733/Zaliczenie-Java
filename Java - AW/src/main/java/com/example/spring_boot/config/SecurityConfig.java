package com.example.spring_boot.config;

import com.example.spring_boot.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Konfiguracja zabezpieczeń Spring Security 6.1+
 * Bez deprecated metod, zgodna z IntelliJ IDEA
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder());
        return authBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(
                                new AntPathRequestMatcher("/login"),
                                new AntPathRequestMatcher("/register"),
                                new AntPathRequestMatcher("/css/**"),
                                new AntPathRequestMatcher("/js/**"),
                                new AntPathRequestMatcher("/images/**"),
                                new AntPathRequestMatcher("/fonts/**"),
                                new AntPathRequestMatcher("/favicon.ico"),
                                new AntPathRequestMatcher("/error"),
                                new AntPathRequestMatcher("/h2-console/**"),
                                new AntPathRequestMatcher("/public/**")
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/listings", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .maximumSessions(3)
                        .maxSessionsPreventsLogin(false)
                        .expiredUrl("/login?expired=true")
                )
                .headers(headers -> headers
                        .httpStrictTransportSecurity(hsts -> hsts
                                .maxAgeInSeconds(31536000)
                                .includeSubDomains(true)
                        )
                        .frameOptions().sameOrigin()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(
                                new AntPathRequestMatcher("/h2-console/**")
                        )
                )
                .exceptionHandling(exceptions ->
                        exceptions.accessDeniedPage("/access-denied")
                )
                .build();
    }
}
