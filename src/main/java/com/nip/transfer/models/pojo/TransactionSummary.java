package com.nip.transfer.models.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class TransactionSummary {
    String summaryId;
    int successfulTransactions;
    int inSufficientFundsTransactions;
    int failedTransactions;
    int totalTransactions;
    LocalDate dateCreated;

}
