package com.nip.transfer.repository;

import com.nip.transfer.models.entity.Transaction;
//import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,String>, JpaSpecificationExecutor<Transaction> {

    List<Transaction> findAll();
    List<Transaction> findAll(Specification<Transaction> specification);
    @Query("SELECT c FROM Transaction c WHERE (:status is null or c.status = :status) and (:userId is null"
            + " or c.destinationUserId = :userId) and (:startDate is null AND :endDate is null or c.transactionDate BETWEEN :startDate AND :endDate)")
    List<Transaction> findAllByStatusAndSourceUserIdAndTransactionDateBetween(String status, String userId, LocalDate startDate,LocalDate endDate);
    List<Transaction> findAllByTransactionDate(LocalDate date);

}
