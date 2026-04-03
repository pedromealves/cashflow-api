package com.pedro.cashflow_api.repositories;

import com.pedro.cashflow_api.entities.Transaction;
import com.pedro.cashflow_api.entities.enums.TransactionCategory;
import com.pedro.cashflow_api.entities.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

// The TransactionRepository will be implemented in runtime by Hibernate + Spring Data JPA
// No need to manually implement it
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Spring reads findByCategory and automatically:
    // SELECT * FROM transactions WHERE category = ?
    // This is called Derived Query
    List<Transaction> findByCategory(TransactionCategory transactionCategory);

    List<Transaction> findByType(TransactionType transactionType);

    @Query("SELECT t FROM Transaction t WHERE LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Transaction> searchByKeyword(@Param("keyword") String keyword);

//    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.type = :type")
//    BigDecimal sumByType(@Param("type") TransactionType type);

    @Query("SELECT t FROM Transaction t WHERE " +
            "(:category IS NULL OR t.category = :category)" +
            " AND (:type IS NULL OR t.type = :type)" +
            " AND (:keyword IS NULL OR LOWER(t.description) LIKE LOWER(CONCAT('%', CAST(:keyword AS string), '%')))")
    List<Transaction> search(@Param("category") TransactionCategory category,
                             @Param("type") TransactionType type,
                             @Param("keyword") String keyword);

}
