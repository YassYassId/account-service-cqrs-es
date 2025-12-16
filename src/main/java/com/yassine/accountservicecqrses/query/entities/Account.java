package com.yassine.accountservicecqrses.query.entities;

import com.yassine.accountservicecqrses.commonapi.enums.AccountStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Entity
@AllArgsConstructor @NoArgsConstructor @Data @Builder
public class Account {
    @Id
    private String id;
    private double balance;
    private String currency;
    private AccountStatus status;
    private Instant createdAt;
    @OneToMany
    private List<AccountTransaction> transactions;
}
