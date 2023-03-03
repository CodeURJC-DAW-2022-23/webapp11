package com.techmarket.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder.passwordEncoder());
    }

    @Autowired
    private EncoderConfiguration passwordEncoder;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .exceptionHandling( exception -> exception
                        .accessDeniedPage("/access-denied")
                )
                .authorizeHttpRequests( auth -> auth
                        .requestMatchers("/signup", "/signin", "/signin-user", "/signup-user", "/", "/product/**", "/search/**", "/error", "access-denied", "/recovery", "/recover-email", "/code", "/verify-code").permitAll()
                        // Access to the assets so the frontend can load correctly
                        .requestMatchers(request -> request.getServletPath().endsWith(".css") || request.getServletPath().endsWith(".js") || request.getServletPath().endsWith(".jpg") || request.getServletPath().endsWith(".png")).permitAll()
                        .requestMatchers("/admin/**", "/addproduct", "/addproduct-create", "/editproduct", "/editproduct-update","/dashboard","/statistics","/pricehistory").hasAuthority("ADMIN")
                        .requestMatchers("/profile").authenticated()  // Any role will be able to access its profile
                        .requestMatchers("/edit-profile").authenticated()
                        .requestMatchers("/wishlist").hasAnyAuthority("USER")
                        .requestMatchers("/checkout").hasAnyAuthority("USER")
                        .requestMatchers("/cart").hasAnyAuthority("USER")
                        .requestMatchers("/messages").hasAnyAuthority("USER")
                        .requestMatchers("/messages/**").hasAnyAuthority("AGENT")
                        .requestMatchers("/chats").hasAnyAuthority("AGENT")
                        .anyRequest().authenticated()
                )
                .formLogin( form -> form
                        .loginPage("/signin")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .loginProcessingUrl("/signin-user")
                        .successHandler(new LoginHandler())
                        .failureUrl("/signin?error")
                )
                .logout( logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                )
                .build();
    }
}
