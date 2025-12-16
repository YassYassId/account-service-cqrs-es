package com.yassine.accountservicecqrses.query.controllers;

import com.yassine.accountservicecqrses.query.entities.Account;
import com.yassine.accountservicecqrses.query.queries.GetAllAccountsQuery;
import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("query/accounts")
@AllArgsConstructor
public class AccountQueryController {
    private QueryGateway queryGateway;

    @GetMapping("/list")
    public CompletableFuture<List<Account>> accounts(){
        CompletableFuture<List<Account>> result = queryGateway.query(new GetAllAccountsQuery(),
                ResponseTypes.multipleInstancesOf(Account.class));
        return result;
    }
}
