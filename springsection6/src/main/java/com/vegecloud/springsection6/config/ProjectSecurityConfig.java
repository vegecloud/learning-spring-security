package com.vegecloud.springsection6.config;

import com.vegecloud.springsection6.exceptionhandler.CustomAccessDeniedHandler;
import com.vegecloud.springsection6.exceptionhandler.CustomBasicAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import static org.springframework.security.config.Customizer.withDefaults;

@Profile("!prod")
@Configuration
public class ProjectSecurityConfig {

    /**
     * Spring Security enables CSRF protection by default for POST, PUT, PATCH, DELETE methods.
     * Hence, to make POST requests we must either configure CSRF or disable it.
     * -
     * Instead of using the default exception handler from Spring Security,
     * we use the httpBasicCustomizer to specify an exception handler through .authenticationEntryPoint().
     */

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(smc -> smc
                .invalidSessionUrl("/invalidSession")
                .maximumSessions(3)
                .maxSessionsPreventsLogin(true))
            .csrf(csrfConfig -> csrfConfig.disable())
            .authorizeHttpRequests(requests -> requests
            .requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards").authenticated()
            .requestMatchers("/notices", "/contact", "/register", "/error", "/invalidSession").permitAll());
        http.formLogin(withDefaults());
        http.httpBasic(hbc -> hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
        http.exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));
        return http.build();
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
