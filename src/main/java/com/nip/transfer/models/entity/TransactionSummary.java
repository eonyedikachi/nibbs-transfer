package com.nip.transfer.models.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class TransactionSummary {

    @Id
    @Column(unique = true)
    String summaryId;
    int successfulTransactions;
    int inSufficientFundsTransactions;
    int failedTransactions;
    int totalTransactions;
    LocalDate dateCreated;
}
