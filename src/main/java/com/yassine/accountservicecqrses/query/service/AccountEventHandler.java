package com.yassine.accountservicecqrses.query.service;

import com.yassine.accountservicecqrses.commonapi.events.AccountCreatedEvent;
import com.yassine.accountservicecqrses.commonapi.events.AccountCreditedEvent;
import com.yassine.accountservicecqrses.commonapi.events.AccountDebitedEvent;
import com.yassine.accountservicecqrses.query.entities.Account;
import com.yassine.accountservicecqrses.query.repositories.AccountRepository;
import com.yassine.accountservicecqrses.query.repositories.AccountTransactionRepository;
import lombok.AllArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountEventHandler {
    private AccountRepository accountRepository;
    private AccountTransactionRepository accountTransactionRepository;

    @EventHandler
    public void on(AccountCreatedEvent event, EventMessage<AccountCreatedEvent> eventMessage){
        Account account = Account.builder()
                .id(event.getId())
                .balance(event.getInitialBalance())
                .currency(event.getCurrency())
                .status(event.getStatus())
                .createdAt(eventMessage.getTimestamp())
                .build();
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountCreditedEvent event){
        Account account = accountRepository.findById(event.getId()).orElseThrow();
        account.setBalance(account.getBalance() + event.getAmount());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountDebitedEvent event) {
        Account account = accountRepository.findById(event.getId()).orElseThrow();
        account.setBalance(account.getBalance() - event.getAmount());
        accountRepository.save(account);
    }
}
