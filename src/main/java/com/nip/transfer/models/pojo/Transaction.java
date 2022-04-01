package com.nip.transfer.models.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class Transaction {
    protected String transactionReference;
    protected String sourceUserId;
    protected String destinationUserId;
    protected String status;
    protected BigDecimal amount;
    protected BigDecimal billedAmount;
    protected String description;
    protected BigDecimal commission;
    protected Boolean commissionWorthy;
    protected BigDecimal transactionFee;
    protected LocalDate transactionDate;
    protected LocalTime transactionTime;
}