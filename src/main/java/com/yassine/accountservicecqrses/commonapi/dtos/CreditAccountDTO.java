package com.yassine.accountservicecqrses.commonapi.dtos;

public record CreditAccountDTO(
        String accountId,
        double amount,
        String currency
) {
}
