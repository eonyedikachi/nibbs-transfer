package com.nip.transfer.repository;

import com.nip.transfer.models.entity.Transaction;
import com.nip.transfer.models.entity.TransactionSummary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionSummaryRepository extends JpaRepository<TransactionSummary,String> {

}
