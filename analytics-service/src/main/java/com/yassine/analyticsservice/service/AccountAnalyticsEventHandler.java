package com.yassine.analyticsservice.service;

import com.yassine.accountservicecqrses.commonapi.events.AccountCreatedEvent;
import com.yassine.accountservicecqrses.commonapi.events.AccountCreditedEvent;
import com.yassine.accountservicecqrses.commonapi.events.AccountDebitedEvent;
import com.yassine.analyticsservice.entities.AccountAnalytics;
import com.yassine.analyticsservice.queries.GetAllAccountAnalytics;
import com.yassine.analyticsservice.queries.GetAllAccountAnalyticsByAccountId;
import com.yassine.analyticsservice.repo.AccountAnalyticsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class AccountAnalyticsEventHandler {
    private AccountAnalyticsRepository accountAnalyticsRepository;
    private QueryUpdateEmitter queryUpdateEmitter;

    @EventHandler
    public void on(AccountCreatedEvent event) {
        log.info("=====================================");
        log.info("AccountCreatedEvent received");
        AccountAnalytics accountAnalytics = AccountAnalytics.builder()
                .accountId(event.getId())
                .balance(event.getInitialBalance())
                .totalCredit(0)
                .totalDebit(0)
                .totalNumberOfCredits(0)
                .totalNumberOfDebits(0)
                .build();
        accountAnalyticsRepository.save(accountAnalytics);
        log.info("AccountAnalytics created for account id: {}", event.getId());
    }

    @EventHandler
    public void on(AccountDebitedEvent event) {
        log.info("=====================================");
        log.info("AccountDebitedEvent received");
        AccountAnalytics accountAnalytics = accountAnalyticsRepository.findByAccountId(event.getId());
        if (accountAnalytics != null) {
            accountAnalytics.setBalance(accountAnalytics.getBalance() - event.getAmount());
            accountAnalytics.setTotalDebit(accountAnalytics.getTotalDebit() + event.getAmount());
            accountAnalytics.setTotalNumberOfDebits(accountAnalytics.getTotalNumberOfDebits() + 1);
            accountAnalyticsRepository.save(accountAnalytics);
            queryUpdateEmitter.emit(GetAllAccountAnalyticsByAccountId.class,
                    query -> query.getAccountId().equals(accountAnalytics.getAccountId()),
                    accountAnalytics);
            log.info("AccountAnalytics updated for account id: {}", event.getId());
        } else {
            log.error("AccountAnalytics not found for account id: {}", event.getId());
        }
    }

    @EventHandler
    public void on(AccountCreditedEvent event) {
        log.info("=====================================");
        log.info("AccountCreditedEvent received");
        AccountAnalytics accountAnalytics = accountAnalyticsRepository.findByAccountId(event.getId());
        if (accountAnalytics != null) {
            accountAnalytics.setBalance(accountAnalytics.getBalance() + event.getAmount());
            accountAnalytics.setTotalCredit(accountAnalytics.getTotalCredit() + event.getAmount());
            accountAnalytics.setTotalNumberOfCredits(accountAnalytics.getTotalNumberOfCredits() + 1);
            accountAnalyticsRepository.save(accountAnalytics);
            queryUpdateEmitter.emit(GetAllAccountAnalyticsByAccountId.class,
                    query -> query.getAccountId().equals(accountAnalytics.getAccountId()),
                    accountAnalytics);
            log.info("AccountAnalytics updated for account id: {}", event.getId());
        } else {
            log.error("AccountAnalytics not found for account id: {}", event.getId());
        }
    }
    @QueryHandler
    public List<AccountAnalytics> on(GetAllAccountAnalytics query) {
        return accountAnalyticsRepository.findAll();
    }
    @QueryHandler
    public AccountAnalytics on(GetAllAccountAnalyticsByAccountId query) {
        return accountAnalyticsRepository.findByAccountId(query.getAccountId());
    }


}