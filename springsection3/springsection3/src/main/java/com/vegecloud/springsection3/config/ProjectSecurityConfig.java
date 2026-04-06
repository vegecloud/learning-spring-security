package com.vegecloud.springsection3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests
            .requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards").authenticated()
            .requestMatchers("/notices", "/contact", "/error").permitAll());
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }

    /**
     * We can use an implementation of the UserDetailsService interface from Spring Security
     * which helps us deal with user accounts (creating, fetching) that are stored in-memory.
     * authorities() allow us to define what authorities or access level the user has.
     */

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User
            .withUsername("user")
            .password("{noop}EazyBytes@12345")
            .authorities("read")
            .build();
        UserDetails admin = User
            .withUsername("admin")
            .password("{bcrypt}$2a$12$FseXXeUxAptDlak4cEmrT.BS7T6NNw3XJ6V5DQfjDFKnQS/RH0ebu")
            .authorities("admin")
            .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

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
