package com.pedro.cashflow_api.services;

import com.pedro.cashflow_api.dto.TransactionSummaryDTO;
import com.pedro.cashflow_api.entities.Transaction;
import com.pedro.cashflow_api.entities.enums.TransactionCategory;
import com.pedro.cashflow_api.entities.enums.TransactionType;
import com.pedro.cashflow_api.exceptions.InvalidRequestException;
import com.pedro.cashflow_api.exceptions.ResourceNotFoundException;
import com.pedro.cashflow_api.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// TO-DO
// * Lançar exceções dentro de updateTransaction

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    // Get all transactions
    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction saveTransaction(Transaction transaction) {
        if (transaction.getAmount() == null || transaction.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidRequestException("The transaction amount must be a positive number.");
        }

        // First checks if the description was sent, then checks if the description is empty
        if (transaction.getDescription() == null || transaction.getDescription().isBlank()) {
            throw new InvalidRequestException("The description must no be empty.");
        }

        if (transaction.getDate() == null) {
            transaction.setDate(LocalDate.now());
        }

        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(Long id, Transaction transaction) {
        Transaction storedTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found."));

        if (transaction.getAmount() != null && transaction.getAmount().compareTo(BigDecimal.ZERO) >= 0) {
            storedTransaction.setAmount(transaction.getAmount());
        }

        if (transaction.getDescription() != null && !transaction.getDescription().isBlank()) {
            storedTransaction.setDescription(transaction.getDescription());
        }

        if (transaction.getDate() != null) {
            storedTransaction.setDate(transaction.getDate());
        }

        if (transaction.getType() != null) {
            storedTransaction.setType(transaction.getType());
        }

        if (transaction.getCategory() != null) {
            storedTransaction.setCategory(transaction.getCategory());
        }

        return transactionRepository.save(storedTransaction);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found: " + id));

        transactionRepository.deleteById(id);
    }

    public List<Transaction> findByCategory(TransactionCategory transactionCategory) {
        if (transactionCategory != null) {
            return (List<Transaction>) transactionRepository.findByCategory(transactionCategory);
        } else {
            //throw new InvalidRequestException("Category not found: " + transactionCategory);
            List<Transaction> emptyTransactionList = new ArrayList<>();
            return emptyTransactionList;
        }
    }

    public List<Transaction> findByType(TransactionType transactionType) {
        if (transactionType != null) {
            return (List<Transaction>) transactionRepository.findByType(transactionType);
        } else {
            List<Transaction> emptyTransactionList = new ArrayList<>();
            return emptyTransactionList;
        }
    }

    public List<Transaction> search(TransactionCategory category, TransactionType type, String keyword) {
//        if (category != null) {
//            return (List<Transaction>) transactionRepository.findByCategory(category);
//        }
//        else if (type != null) {
//            return (List<Transaction>) transactionRepository.findByType(type);
//        } else if (keyword != null && !keyword.isBlank()) {
//            return (List<Transaction>) transactionRepository.searchByKeyword(keyword);
//        } else {
//            return (List<Transaction>) transactionRepository.findAll();
//        }


        return (List<Transaction>) transactionRepository.search(category, type, keyword);
    }

    public TransactionSummaryDTO caculateSummary() {
        List<Transaction> transactions = transactionRepository.findAll();

        BigDecimal incomeSum  = transactions.stream()
                .filter(transaction -> transaction.getType() == TransactionType.INCOME)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal expenseSum = transactions.stream()
                .filter(transaction -> transaction.getType() == TransactionType.EXPENSE)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal balance = incomeSum.subtract(expenseSum);

        return new TransactionSummaryDTO(incomeSum, expenseSum, balance);

    }
}
