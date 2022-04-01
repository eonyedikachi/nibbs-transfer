package com.nip.transfer.scheduler;

import com.nip.transfer.models.entity.Transaction;
import com.nip.transfer.models.entity.TransactionSummary;
import com.nip.transfer.repository.TransactionRepository;
import com.nip.transfer.repository.TransactionSummaryRepository;
import com.nip.transfer.service.TransactionSummaryServiceImpl;
import com.nip.transfer.utils.StatusMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
@EnableScheduling
public class TransactionScheduler {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionSummaryServiceImpl transactionSummaryService;

    @Autowired
    private TransactionSummaryRepository transactionSummaryRepository;

    private BigDecimal getTransactionCommission(BigDecimal transactionFee){
        double COMMISSION_PERCENTAGE = 0.2;
            return transactionFee.multiply(BigDecimal.valueOf(COMMISSION_PERCENTAGE));
    }


//    @Scheduled(cron = "[Seconds] [Minutes] [Hours] [Day of month] [Month] [Day of week] [Year]")
    @Scheduled(cron = "0 59 23 * * ?")
    public void updateTransactionCommission(){
       log.info("Collating commission for all transactions...");
        List <Transaction> transactions = transactionRepository.findAll();
        List<Transaction> successfulTransactions = new ArrayList<>();

        if (transactions != null) {
            transactions.forEach(transaction -> {
                if (transaction.getStatus().equals(StatusMessage.TRANSACTION_SUCCESS)) {
                    BigDecimal commission = getTransactionCommission(transaction.getTransactionFee());
                    transaction.setCommission(commission);
                    transaction.setCommissionWorthy(true);
                    successfulTransactions.add(transaction);
                }
            });
            transactionRepository.saveAll(successfulTransactions);
        }
        log.info("Transactions commission collated and saved");

    }

    @Scheduled(cron = "0 59 23 * * ?")
    public void createTransactionSummary(){
        LocalDate date = LocalDate.now();
        log.info("Creating transaction summary...");
        List<Transaction> transactions = transactionRepository.findAllByTransactionDate(date);
        TransactionSummary transactionSummary = transactionSummaryService.collateSummary(transactions);
        transactionSummaryRepository.save(transactionSummary);
        log.info("Transaction summary created");
    }

}
