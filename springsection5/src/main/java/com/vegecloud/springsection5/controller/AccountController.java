package com.vegecloud.springsection5.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @GetMapping("/myAccount")
    public String getAccountDetails(Authentication authentication) {
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "Here are the account details from the DB, " + authentication.getName();
    }
}
