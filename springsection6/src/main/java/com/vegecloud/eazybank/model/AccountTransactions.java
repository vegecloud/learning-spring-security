package com.vegecloud.eazybank.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class AccountTransactions {
    @Id
    private String transactionId;
    private long accountNumber;
    private long customerId;
    private Date transactionDt;
    private String transactionSummary;
    private String transactionType;
    private int transactionAmt;
    private int closingBalance;
    private Date createDt;
}
