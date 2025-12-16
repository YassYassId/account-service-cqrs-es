package com.yassine.accountservicecqrses.commands.controllers;

import com.yassine.accountservicecqrses.commonapi.commands.CreateAccountCommand;
import com.yassine.accountservicecqrses.commonapi.commands.CreditAccountCommand;
import com.yassine.accountservicecqrses.commonapi.dtos.CreateAccountDTO;
import com.yassine.accountservicecqrses.commonapi.dtos.CreditAccountDTO;
import com.yassine.accountservicecqrses.commonapi.dtos.DebitAccountDTO;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/commands/account")
@AllArgsConstructor
public class AccountCommandController {

    private CommandGateway commandGateway;
    private EventStore eventStore;

    @PostMapping("/create")
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountDTO request){
        CompletableFuture<String> result = commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID().toString(),
                request.currency(),
                request.initialBalance()));
        return result;
    }

    @PostMapping("/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountDTO request){
        CompletableFuture<String> result = commandGateway.send(new CreditAccountCommand(
                request.accountId(),
                request.amount(),
                request.currency()));
        return result;
    }

    @PostMapping("/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountDTO request) {
        CompletableFuture<String> result = commandGateway.send(new CreditAccountCommand(
                request.accountId(),
                request.amount(),
                request.currency()));
        return result;
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionHandler(Exception exception) {
        return ResponseEntity.internalServerError().body(exception.getMessage());
    }

    @GetMapping("/eventStore/{accountId}")
    public Stream eventStore(@PathVariable String accountId){
        return eventStore.readEvents(accountId).asStream();
    }
}
