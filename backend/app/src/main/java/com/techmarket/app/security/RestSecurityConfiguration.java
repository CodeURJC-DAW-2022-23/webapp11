package com.techmarket.app.security;

import com.techmarket.app.security.jwt.JwtRequestFilter;
import com.techmarket.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;

@Controller
@EnableWebSecurity
public class RestSecurityConfiguration extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    @Autowired
    private UserService userService;
    @Autowired
    private EncoderConfiguration passwordEncoder;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    //Expose AuthenticationManager as a Bean to be used in other services
    //@Bean
    //@Override
    //public AuthenticationManager authenticationManagerBean() throws Exception {
    //    return super.authenticationManagerBean();
    //}
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder.passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

    @Bean
    public FilterRegistrationBean<JwtRequestFilter> jwtRequestFilterRegistration() {
        FilterRegistrationBean<JwtRequestFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(jwtRequestFilter);
        registration.addUrlPatterns("/api/*");
        return registration;
    }
    @Bean
    @Order(1)
    SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception
    {
        http
                .securityMatcher("/api/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/auth/signup").permitAll()
                        .requestMatchers("/api/auth/logout").permitAll()
                        .requestMatchers("/api/images/**").authenticated()
                        .requestMatchers("/api/images/d/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/api/products").permitAll()
                        .requestMatchers("/api/products/info/**").permitAll()
                        .requestMatchers("/api/products/search/**").permitAll()
                        .requestMatchers("/api/products/add-product").hasAnyAuthority("ADMIN")
                        .requestMatchers("/api/cart/**").hasAnyAuthority("USER","AGENT")
                        .requestMatchers("/api/wishlist/**").hasAnyAuthority("USER","AGENT")
                        .requestMatchers("/api/recommendations").hasAnyAuthority("USER","AGENT")
                        .requestMatchers("/api/reviews/delete/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/api/reviews/get/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/api/reviews/create/**").hasAnyAuthority("USER","AGENT")
                        .requestMatchers("/api/reviews/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/api/user/send-message/agent/**").hasAnyAuthority("AGENT")
                        .requestMatchers("/api/user/send-message").hasAnyAuthority("USER")
                        .requestMatchers("/api/user/messages").hasAnyAuthority("AGENT", "ADMIN", "USER")
                        .requestMatchers("/api/user/profile").hasAnyAuthority("USER", "AGENT", "ADMIN")
                        .requestMatchers("/api/user/messages/agent/**").hasAnyAuthority("AGENT")
                        .requestMatchers("/api/purchase-history").hasAnyAuthority("USER", "AGENT")

                )
                .httpBasic().disable()
                .formLogin().disable();
        // Disable csrf only for the api
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
