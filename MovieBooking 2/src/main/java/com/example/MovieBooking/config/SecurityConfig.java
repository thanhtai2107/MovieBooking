package com.example.MovieBooking.config;

import com.example.MovieBooking.service.IAccountService;
import com.example.MovieBooking.util.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private IAccountService accountService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> {
                    request.requestMatchers("/register",
                            "/login","/home"
                    ).permitAll();
                    request.requestMatchers("/member-management/**","/showtimes", "/admin/**").hasAnyAuthority("ADMIN", "EMPLOYEE");
                    request.requestMatchers("/admin/booking-list",
                            "/admin/confirm-booking/"
                            ,"/cinemaRoom/**").hasAuthority("ADMIN");
                    request.anyRequest().authenticated();
                }).formLogin(form -> form.loginPage("/login").successHandler(loginSuccessHandler()).permitAll())
                .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout")));

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return accountService;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }
}
