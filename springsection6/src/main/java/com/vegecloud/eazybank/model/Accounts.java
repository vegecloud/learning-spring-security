package com.vegecloud.eazybank.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Accounts {
    @Id
    private long accountNumber;
    private long customerId;
    private String accountType;
    private String branchAddress;
    private Date createDt;
}
