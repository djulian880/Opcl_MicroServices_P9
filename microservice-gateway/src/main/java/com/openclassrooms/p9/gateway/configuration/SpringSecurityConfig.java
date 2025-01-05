package com.openclassrooms.p9.gateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;

@Configuration
@EnableWebFluxSecurity


public class SpringSecurityConfig {

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        // Créez un encodeur pour sécuriser les mots de passe
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        //System.out.println("Encoded password for 'user': " + encoder.encode("user"));
        //System.out.println("Encoded password for 'admin': " + encoder.encode("admin"));

        // Définir les utilisateurs
        UserDetails user = User.withUsername("user")
                .password(encoder.encode("user123"))
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password(encoder.encode("admin"))
                .roles("ADMIN")
                .build();

        // Retournez le service en mémoire
        return new MapReactiveUserDetailsService(user, admin);
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/public/**").permitAll() // Routes publiques
                        .pathMatchers("/Patients/**").permitAll() // Routes publiques
                        .pathMatchers("/admin/**").hasRole("ADMIN") // Routes accessibles uniquement aux admins
                        .pathMatchers("/**").hasRole("USER")
                        .anyExchange().authenticated() // Toutes les autres routes nécessitent une authentification
                )
                .httpBasic().and() // Active l'authentification HTTP Basic
                //.httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(new BasicAuthenticationEntryPoint())) // Nouvelle approche pour HTTP Basic
                //.httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(new CustomAuthenticationEntryPoint())) // Utilisation de l'entrée personnalisée
                //.httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(new HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED))) // Utilisation de HttpStatusServerEntryPoint

                .csrf().disable(); // Désactive CSRF
        ;
        return http.build();
    }

}
