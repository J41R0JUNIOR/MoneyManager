package com.demo.config;

import com.demo.model.User;
import com.demo.service.UserDetailsServiceImpl;
import com.demo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import java.util.Optional;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserAuthenticationFilter userAuthenticationFilter) throws Exception {
        http
//                .csrf(csrf -> csrf
//                .ignoringRequestMatchers("/auth/**")
//                )
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("user/getAll").permitAll()
                .anyRequest().authenticated()
                )

                .sessionManagement(session -> session
                  .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, UserAuthenticationFilter userAuthenticationFilter) throws Exception {
//        return httpSecurity
//                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/auth/**").permitAll()
//                        .requestMatchers("/user/getAll").permitAll()
//                        // .requestMatchers(ENDPOINTS_ADMIN).hasRole("ADMINISTRATOR")
//                        // .requestMatchers(ENDPOINTS_CUSTOMER).hasRole("CUSTOMER")
//                        .anyRequest().denyAll()
//                )
//                .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, UserAuthenticationFilter userAuthenticationFilter) throws Exception {
//        return httpSecurity.
//                csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and().authorizeHttpRequests()
////                .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
////                .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_REQUIRED).authenticated()
//                .requestMatchers("/auth/**").permitAll()
//                .requestMatchers("user/getAll").permitAll()
////                .requestMatchers(ENDPOINTS_ADMIN).hasRole("ADMINISTRATOR") // Repare que não é necessário colocar "ROLE" antes do nome, como fizemos na definição das roles
////                .requestMatchers(ENDPOINTS_CUSTOMER).hasRole("CUSTOMER")
//                .anyRequest().denyAll()
//                .and().addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//    @Bean
//    public AuthenticationManager authenticationManager(
//            UserDetailsService userDetailsService,
//            PasswordEncoder passwordEncoder
//    ) {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(userDetailsService);
//        authenticationProvider.setPasswordEncoder(passwordEncoder);
//
//        return new ProviderManager(authenticationProvider);
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return email -> {
//            UserDetails user = userServiceImpl.loadUserByUsername(email);
//
//            return org.springframework.security.core.userdetails.User
//                .withUsername(user.getUsername())
//                .password(passwordEncoder().encode(user.getPassword()))
//                .roles("USER")
//                .build();
//        };
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }

}