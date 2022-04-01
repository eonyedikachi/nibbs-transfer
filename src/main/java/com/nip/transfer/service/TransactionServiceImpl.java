package com.nip.transfer.service;

import com.nip.transfer.exception.TransferException;
import com.nip.transfer.models.entity.User;
import com.nip.transfer.repository.TransactionRepository;
import com.nip.transfer.models.entity.Transaction;
import com.nip.transfer.models.pojo.request.Transfer;
import com.nip.transfer.repository.TransactionSpecification;
import com.nip.transfer.utils.StatusMessage;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService{

    private  ModelMapper mapper = new ModelMapper();
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Override
    public com.nip.transfer.models.pojo.Transaction processTransaction(Transfer transfer) {
        Transaction transaction = new Transaction();
        String uuid = UUID.randomUUID().toString();
            try {
                log.info("Processing transaction...");
                transaction.setTransactionReference(uuid);
                transaction.setAmount(transfer.getAmount());
                transaction.setDestinationUserId(transfer.getDestinationUserId());
                transaction.setSourceUserId(transfer.getSourceUserId());
                transaction.setTransactionDate(LocalDate.now());
                transaction.setTransactionTime(LocalTime.now());

                User sourceUser = userServiceImpl.getUser(transfer.getSourceUserId());
                User destinationUser = userServiceImpl.getUser(transfer.getDestinationUserId());
                if (sourceUser != null) {

                    if(destinationUser != null) {
                        BigDecimal transactionFee = computeTransactionFee(transaction.getAmount());
                        BigDecimal billedAmount = transaction.getAmount().add(transactionFee);
                        if (checkBalance(sourceUser.getBalance(), billedAmount)) {
                            sourceUser.setBalance(sourceUser.getBalance().subtract(transfer.getAmount()));
                            sourceUser.setDateUpdated(LocalDate.now());
                            userServiceImpl.updateUser(sourceUser);

                            destinationUser.setBalance(sourceUser.getBalance().add(transfer.getAmount()));
                            destinationUser.setDateUpdated(LocalDate.now());
                            userServiceImpl.updateUser(destinationUser);

                            transaction.setBilledAmount(billedAmount);
                            transaction.setDescription("Transaction Successful");
                            transaction.setStatus(StatusMessage.TRANSACTION_SUCCESS);
                            transaction.setTransactionFee(transactionFee);
                            transactionRepository.save(transaction);
                            log.info("Transaction successful");
                        } else {
                            // lodge transaction due to insufficient balance
                            transaction.setDescription("Could not process transaction due to insufficient balance");
                            transaction.setStatus(StatusMessage.INSUFFICIENT_BALANCE);
                            transactionRepository.save(transaction);
                            log.error("Error: {}", StatusMessage.INSUFFICIENT_BALANCE);
                            throw new TransferException(StatusMessage.INSUFFICIENT_BALANCE);
                        }
                    } else{
                        transaction.setDescription("Unknown transaction destination user");
                        transaction.setStatus(StatusMessage.TRANSACTION_FAILED);
                        transactionRepository.save(transaction);
                        log.error("Error: {}", StatusMessage.TRANSACTION_FAILED);

                        throw new TransferException(StatusMessage.TRANSACTION_FAILED);
                    }

                } else{
                    transaction.setDescription("Unknown transaction source user");
                    transaction.setStatus(StatusMessage.TRANSACTION_FAILED);
                    transactionRepository.save(transaction);
                    log.error("Error: {}", StatusMessage.TRANSACTION_FAILED);
                    throw new TransferException(StatusMessage.TRANSACTION_FAILED);
                }

            }catch (Exception e) {
                transaction.setDescription(e.getMessage());
                transaction.setStatus(StatusMessage.TRANSACTION_FAILED);
                transactionRepository.save(transaction);
                log.error("Error: {}", StatusMessage.TRANSACTION_FAILED);
                throw new TransferException(StatusMessage.TRANSACTION_FAILED + ": " + e.getMessage(), e.getCause());
            }
        return mapper.map(transaction, com.nip.transfer.models.pojo.Transaction.class);

    }

    @Override
    public List<com.nip.transfer.models.pojo.Transaction> getAllTransactions(String status, String userId, LocalDate startDate,LocalDate endDate) {

        log.info("Getting transactions with query parameters: {}, {}, {}, {}", status, userId, startDate, endDate);
        Specification<Transaction> transactionSpecification = TransactionSpecification.getFilteredTransactions(status, userId, startDate, endDate);
        List<com.nip.transfer.models.pojo.Transaction> transaction = new ArrayList<>();

        List<Transaction> transactions = transactionRepository.findAll(transactionSpecification);
            if (transactions.size() > 0){
             transaction = transactions.stream().map(
                            transaction1 -> mapper.map(transaction1, com.nip.transfer.models.pojo.Transaction.class))
                    .collect(Collectors.toList());
             log.info("Successfully fetched transactions with query parameters: {}, {}, {}, {}", status, userId, startDate, endDate);
        }

        return transaction;
    }


    private boolean checkBalance(BigDecimal balance, BigDecimal requestAmount){
        log.info("Checking balance...");
        final int SUFFICIENT_BALANCE = 1;
        int check = balance.compareTo(requestAmount);
        return check == SUFFICIENT_BALANCE;
    }

    private BigDecimal computeTransactionFee(BigDecimal amount){
        log.info("Computing transaction fee...");
        final int BELOW_CAP = -1;
        final double TRANSACTION_FEE_CAP = 100.0;
        final double TRANSACTION_FEE_PERCENTAGE = 0.005;
        int check = amount.compareTo(BigDecimal.valueOf(TRANSACTION_FEE_CAP));

        if( check == BELOW_CAP) {
            return amount.multiply(BigDecimal.valueOf(TRANSACTION_FEE_PERCENTAGE));
        } else
            return BigDecimal.valueOf(TRANSACTION_FEE_CAP);
    }
}
