package com.yassine.accountservicecqrses.commonapi.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class CreateAccountCommand extends BaseCommand<String> {
    @Getter private String currency;
    @Getter private double initialBalance;

    public CreateAccountCommand(String id, String currency, double initialBalance) {
        super(id);
        this.currency = currency;
        this.initialBalance = initialBalance;
    }
}
