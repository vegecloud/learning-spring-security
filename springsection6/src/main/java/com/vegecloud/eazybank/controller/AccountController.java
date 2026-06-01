package com.vegecloud.eazybank.controller;

import com.vegecloud.eazybank.model.Accounts;
import com.vegecloud.eazybank.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountRepository accountRepository;

    @GetMapping("/myAccount")
    public Accounts getAccountDetails(@RequestParam long id) {
        Accounts accounts = accountRepository.findByCustomerId(id);
        if (accounts != null) {
            return accounts;
        }
        return null;
    }
}
