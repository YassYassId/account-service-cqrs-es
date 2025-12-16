package com.yassine.accountservicecqrses.commonapi.dtos;

public record CreateAccountDTO(
        String currency,
        double initialBalance
) {
}
