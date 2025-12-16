package com.yassine.accountservicecqrses.query.entities;

import com.yassine.accountservicecqrses.commonapi.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class AccountTransaction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double amount;
    private Instant instant;
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    @ManyToOne
    private Account account;
}
