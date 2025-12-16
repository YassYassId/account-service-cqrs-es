package com.yassine.accountservicecqrses.commonapi.dtos;

public record DebitAccountDTO(
    String accountId,
    double amount,
    String currency
){}
