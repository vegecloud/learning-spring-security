package com.vegecloud.springsection4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurityConfig {

    /**
     * Spring Security enables CSRF protection by default for POST, PUT, PATCH, DELETE methods.
     * Hence, to make POST requests we must either configure CSRF or disable it.
     */

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrfConfig -> csrfConfig.disable())
            .authorizeHttpRequests(requests -> requests
            .requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards").authenticated()
            .requestMatchers("/notices", "/contact", "/register", "/error").permitAll());
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }

    /**
     * We return the JdbcUserDetailsManager object with the constructor that accepts
     * the DataSource object as a parameter, allowing Jdbc to know our connection details
     * based on the properties specified in our application.yaml file from the DataSource object.
     */

//    Comment out for custom implementation of UserDetailsService
//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource) {
//        return new JdbcUserDetailsManager(dataSource);
//    }

    /**
     * createDelegatingPasswordEncoder() supports many types of password encoders.
     * password() doesn't require a {noop} specifier when we use a password encoder
     * because Spring Security will rely on the default encoder defined in the method.
     */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     *  HaveIBeenPwnedRestApiPasswordChecker() is an API that checks if a password has been compromised.
     *  We can also define our own implementation by implementing the CompromisedPasswordChecker interface.
     */

    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }

}
