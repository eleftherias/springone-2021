package com.example.springone2021;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(withDefaults())
                .authorizeRequests(authz -> authz
                    .antMatchers(HttpMethod.GET, "/about").permitAll()
                    .antMatchers(HttpMethod.POST, "/about").hasRole("ADMIN")
                    .antMatchers("/submissions").hasRole("SPEAKER")
                    .anyRequest().authenticated()
                );
        return http.build();
    }

}
