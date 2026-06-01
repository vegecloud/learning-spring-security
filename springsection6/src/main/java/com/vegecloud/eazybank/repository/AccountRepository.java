package com.vegecloud.eazybank.repository;

import com.vegecloud.eazybank.model.Accounts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Accounts, Long> {
    Accounts findByCustomerId(long customerId);
}
