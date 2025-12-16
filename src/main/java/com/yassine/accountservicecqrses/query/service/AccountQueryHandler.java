package com.yassine.accountservicecqrses.query.service;


import com.yassine.accountservicecqrses.query.entities.Account;
import com.yassine.accountservicecqrses.query.queries.GetAllAccountsQuery;
import com.yassine.accountservicecqrses.query.repositories.AccountRepository;
import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AccountQueryHandler {
    private AccountRepository accountRepository;

    @QueryHandler
    public List<Account> on(GetAllAccountsQuery query){
        return accountRepository.findAll();
    }
}
