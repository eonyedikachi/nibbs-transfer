package com.nip.transfer.service;

import com.nip.transfer.models.entity.Transaction;
import com.nip.transfer.models.pojo.TransactionSummary;
import com.nip.transfer.repository.TransactionRepository;
import com.nip.transfer.repository.TransactionSummaryRepository;
import com.nip.transfer.utils.StatusMessage;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class TransactionSummaryServiceImpl implements TransactionSummaryService {

    private ModelMapper mapper = new ModelMapper();

    @Autowired
    TransactionSummaryRepository transactionSummaryRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public TransactionSummary createSummary(LocalDate date) {
        log.info("Getting all transactions for {}", date);
            List<Transaction> transactions = transactionRepository.findAllByTransactionDate(date);

            com.nip.transfer.models.entity.TransactionSummary entity = collateSummary(transactions);

       transactionSummaryRepository.save(entity);

        log.info("Transactions summary created and saved");
            return mapper.map(entity, TransactionSummary.class);
    }

    public com.nip.transfer.models.entity.TransactionSummary collateSummary( List<Transaction> transactions){
        com.nip.transfer.models.entity.TransactionSummary result = new com.nip.transfer.models.entity.TransactionSummary();
        var ref = new Object() {
            int successfulTransactions = 0;
            int inSufficientFundsTransactions = 0;
            int failedTransactions = 0;
        };

        if (transactions != null){
            transactions.forEach(transaction -> {
                String status = transaction.getStatus();
                if (status.equals(StatusMessage.TRANSACTION_SUCCESS)){
                    ref.successfulTransactions++;
                } else if (status.equals(StatusMessage.INSUFFICIENT_BALANCE)){
                    ref.inSufficientFundsTransactions++;
                } else
                    ref.failedTransactions++;
            });
            String uuid = UUID.randomUUID().toString();
            result.setSummaryId(uuid);
            result.setSuccessfulTransactions(ref.successfulTransactions);
            result.setInSufficientFundsTransactions(ref.inSufficientFundsTransactions);
            result.setFailedTransactions(ref.failedTransactions);
            result.setTotalTransactions(transactions.size());
            result.setDateCreated(LocalDate.now());

        }
        return result;
    }
}
