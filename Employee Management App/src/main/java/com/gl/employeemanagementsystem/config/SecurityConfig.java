package com.gl.employeemanagementsystem.config;

import com.gl.employeemanagementsystem.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final PassEncoder passwordEncoder;

    @Bean
    AuthenticationProvider authProvider() {
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        dao.setPasswordEncoder(passwordEncoder.passwordEncoder());
        dao.setUserDetailsService(customUserDetailsService);
        return dao;
    }

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // Security filter configuration
        http.authorizeHttpRequests(
                auth -> auth
                        // Permit certain URLs without authentication
                        .requestMatchers("/login", "/logout", "/api/roles**", "api/users**").permitAll()
                        // Authorization rules based on HTTP methods and URL patterns
                        .requestMatchers(HttpMethod.GET, "/api/employees/").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/api/employees", "/api/employees/{id}").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/employees", "/api/employees/{id}").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/roles", "/api/users").permitAll()
                        // Require authentication for any other request
                        .anyRequest().authenticated());

        // Disable CORS, CSRF, and frame options
        http.cors(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);
        http.httpBasic(Customizer.withDefaults());
        http.formLogin(Customizer.withDefaults());
        http.headers(customizer -> customizer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        return http.build();
    }
}
