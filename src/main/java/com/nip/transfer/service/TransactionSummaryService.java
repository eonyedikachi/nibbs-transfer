package com.nip.transfer.service;

import com.nip.transfer.models.pojo.TransactionSummary;


import java.time.LocalDate;

public interface TransactionSummaryService {

    TransactionSummary createSummary(LocalDate date);
}
