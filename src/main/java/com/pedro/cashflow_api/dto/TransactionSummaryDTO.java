package com.pedro.cashflow_api.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class TransactionSummaryDTO {
    private BigDecimal income;
    private BigDecimal expense;
    private BigDecimal balance;


    public TransactionSummaryDTO(BigDecimal income, BigDecimal expense, BigDecimal balance) {
        this.income = income;
        this.expense = expense;
        this.balance = balance;
    }
}


