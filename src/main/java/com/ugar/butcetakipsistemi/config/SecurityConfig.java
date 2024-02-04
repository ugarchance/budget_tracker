package com.ugar.butcetakipsistemi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails ahmet = User.builder()
                .username("ahmet")
                .password("{noop}ugar")
                .roles("AHMET")
                .build();
        UserDetails mustafa = User.builder()
                .username("mustafa")
                .password("{noop}rami")
                .roles("MUSTAFA")
                .build();
        UserDetails hilmi = User.builder()
                .username("hilmi")
                .password("{noop}gür")
                .roles("HİLMİ")
                .build();
        return new InMemoryUserDetailsManager(ahmet, mustafa, hilmi);

    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer -> configurer
                        .anyRequest().authenticated()
                ).formLogin(form ->
                        form
                                .loginPage("/showMyLoginPage")
                                .loginProcessingUrl("/authenticateTheUser")
                                .permitAll()

                )
                .logout(logout -> logout.permitAll()
                );

        return http.build();
    }

}
