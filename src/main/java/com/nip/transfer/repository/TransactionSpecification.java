package com.nip.transfer.repository;

import com.nip.transfer.models.entity.Transaction;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionSpecification {
    public static Specification<Transaction> getFilteredTransactions(String status, String userId, LocalDate startDate, LocalDate endDate) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (status != null)
            predicates.add(builder.equal(root.get("status"), status));
            if (userId != null)
            predicates.add(builder.equal(root.get("sourceUserId"), userId));
            if (startDate != null && endDate != null)
            predicates.add(builder.between(root.<LocalDate>get("transactionDate"), startDate, endDate));
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
