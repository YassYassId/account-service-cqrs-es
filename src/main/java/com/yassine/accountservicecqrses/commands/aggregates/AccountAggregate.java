package com.yassine.accountservicecqrses.commands.aggregates;

import com.yassine.accountservicecqrses.commonapi.commands.CreateAccountCommand;
import com.yassine.accountservicecqrses.commonapi.commands.CreditAccountCommand;
import com.yassine.accountservicecqrses.commonapi.commands.DebitAccountCommand;
import com.yassine.accountservicecqrses.commonapi.enums.AccountStatus;
import com.yassine.accountservicecqrses.commonapi.events.AccountCreatedEvent;
import com.yassine.accountservicecqrses.commonapi.events.AccountCreditedEvent;
import com.yassine.accountservicecqrses.commonapi.events.AccountDebitedEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private String currency;
    private AccountStatus status;

    public AccountAggregate() {
    }

    @CommandHandler
    public AccountAggregate(CreateAccountCommand command) {
        if (command.getInitialBalance() < 0) {
            throw new RuntimeException("Initial balance cannot be negative");
        }
        AggregateLifecycle.apply(new AccountCreatedEvent(
                command.getId(),
                command.getInitialBalance(),
                command.getCurrency(),
                AccountStatus.CREATED
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        this.accountId = event.getId();
        this.balance = event.getInitialBalance();
        this.currency = event.getCurrency();
        this.status = event.getStatus();
    }

    @CommandHandler
    public void handle(CreditAccountCommand command) {
        if (command.getAmount() < 0) {
            throw new RuntimeException("Amount to credit cannot be negative");
        }
        AggregateLifecycle.apply(new AccountCreditedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency()
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreditedEvent event) {
        this.balance += event.getAmount();
    }

    @CommandHandler
    public void handle(DebitAccountCommand command) {
        if (command.getAmount() < 0) {
            throw new RuntimeException("Amount to debit cannot be negative");
        }
        if (this.balance < command.getAmount()) {
            throw new RuntimeException("Insufficient balance");
        }
        AggregateLifecycle.apply(new AccountDebitedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency()
        ));
    }

    @EventSourcingHandler
    public void on(AccountDebitedEvent event) {
        this.balance -= event.getAmount();
    }
}
