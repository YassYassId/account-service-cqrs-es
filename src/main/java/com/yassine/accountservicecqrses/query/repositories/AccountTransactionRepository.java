package com.yassine.accountservicecqrses.query.repositories;

import com.yassine.accountservicecqrses.query.entities.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Long> {
}
