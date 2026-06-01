package com.vegecloud.eazybank.controller;

import com.vegecloud.eazybank.model.Customer;
import com.vegecloud.eazybank.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
        try {
            String hashPwd = passwordEncoder.encode(customer.getPwd());
            customer.setPwd(hashPwd);
            customer.setCreateDt(new Date(System.currentTimeMillis()));
            Customer savedCustomer = customerRepository.save(customer);

            if (savedCustomer.getId() > 0) {
                return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("User details are successfully registered");
            } else {
                return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("User registration failed");
            }
        } catch (Exception ex) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Exception occured: " + ex.getMessage());
        }
    }

    /**
     *  Since this api is authenticated, Spring Security will try to authenticate the request
     *  by looking at the request header for an Authorization header. If the UI application is sending
     *  the user credentials in the header in the form of HttpBasic, the framework will take care of
     *  authenticating the end user automatically. Once the authentication is successful, this api will be invoked.
     */

    @RequestMapping("/user")
    public Customer getUserDetailsAfterLogin(Authentication authentication) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(authentication.getName());
        return optionalCustomer.orElse(null);
    }
}
