package ru.nokkov.blps_lab.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/articles").permitAll()
                        .requestMatchers("/articles/{id}").permitAll()
                        .requestMatchers("/add-comment/{id}").permitAll()
                        .requestMatchers("/all-articles").hasRole("MODERATOR")
                        .requestMatchers("/create-article").hasRole("MODERATOR")
                        .requestMatchers("/articles/{id}").hasRole("MODERATOR")
                        .requestMatchers("/articles/{id}/publish").hasRole("MANAGER")
                        .requestMatchers("/article/{id}/views").hasRole("MANAGER")
                        .requestMatchers("/article/{id}/accept").hasRole("ADMIN")
                        .anyRequest().permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
