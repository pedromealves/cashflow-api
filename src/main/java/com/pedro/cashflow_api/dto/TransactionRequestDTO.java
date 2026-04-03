package com.pedro.cashflow_api.dto;

import com.pedro.cashflow_api.entities.Transaction;
import com.pedro.cashflow_api.entities.enums.TransactionCategory;
import com.pedro.cashflow_api.entities.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

// The setters inside the class will be used in "@RequestBody TransactionRequestDTO transactionRequestDTO"
// Inside the controller through Hibernate as it fills the object
public class TransactionRequestDTO {
    private String description;
    private BigDecimal amount;
    private LocalDate date;
    private TransactionType type;
    private TransactionCategory category;

    public Transaction transformIntoObject() {
        Transaction transaction = new Transaction();
        transaction.setDescription(this.description);
        transaction.setAmount(this.amount);
        transaction.setDate(this.date);
        transaction.setType(this.type);
        transaction.setCategory(this.category);

        return transaction;
    }

    public String getDescription() {
        return this.description;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public TransactionType getType() { return this.type; }

    public TransactionCategory getCategory() { return this.category; }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setType(TransactionType type) { this.type = type; }

    public void setCategory(TransactionCategory category) { this.category = category; }

    // LocalDate.now() should not be here because the framework may not work properly
    public void setDate(LocalDate date) {
        this.date = date;
    }

}
