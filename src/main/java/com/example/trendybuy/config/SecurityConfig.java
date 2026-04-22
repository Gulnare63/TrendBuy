// file: src/main/java/com/example/trendybuy/config/SecurityConfig.java
package com.example.trendybuy.config;

import com.example.trendybuy.security.JwtAuthenticationFilter;
import com.example.trendybuy.service.impl.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Public auth endpointlər
                        .requestMatchers(
                                "/api/auth/register/email",
                                "/api/auth/register/phone",
                                "/api/auth/verify/email-otp",
                                "/api/auth/verify/phone-otp",
                                "/api/auth/login",
                                "/api/auth/login/verify-otp",
                                "/api/auth/forgot-password",
                                "/api/auth/reset-password",
                                "/api/auth/refresh",
                                "/api/seller/register"
                        ).permitAll()
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        // Swagger, h2 və s. varsa burda əlavə edərsən
                        .requestMatchers(
                                "/api/categories/**",
                                "/api/products/**"
                        ).permitAll()
                        // Protected auth endpointlər
                        .requestMatchers(
                                "/api/auth/me",
                                "/api/auth/change-password",
                                "/api/auth/logout"
                        ).authenticated()
                        // 🧾 Seller-only endpointlər
                        .requestMatchers("/api/seller/**", "/api/product-images/**").hasRole("SELLER")

                        // 🧑‍💻 Admin-only (sonra əlavə edəcəksən)
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        // qalan hər şey - hazırda auth tələb etsin
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
