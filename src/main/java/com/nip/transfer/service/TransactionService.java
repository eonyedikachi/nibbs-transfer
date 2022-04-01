package com.nip.transfer.service;

import com.nip.transfer.models.pojo.request.Transfer;
import com.nip.transfer.models.pojo.Transaction;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TransactionService {

    Transaction processTransaction(Transfer transfer);
    List<Transaction> getAllTransactions(String status, String userId, LocalDate startDate, LocalDate endDate);
}
