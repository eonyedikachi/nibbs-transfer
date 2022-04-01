package com.nip.transfer.models.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class Transaction {
    @Id
    @Column(unique = true)
    protected String transactionReference;

    @Column(nullable = false)
    protected String sourceUserId;

    @Column(nullable = false)
    protected String destinationUserId;
    protected String status;

    @Column(nullable = false)
    protected BigDecimal amount;
    protected BigDecimal billedAmount;
    protected String description;
    protected Boolean commissionWorthy;
    protected BigDecimal commission;
    protected BigDecimal transactionFee;
    protected LocalDate transactionDate;
    protected LocalTime transactionTime;
}
