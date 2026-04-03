package com.pedro.cashflow_api.dto;

import com.pedro.cashflow_api.entities.Transaction;
import com.pedro.cashflow_api.entities.enums.TransactionCategory;
import com.pedro.cashflow_api.entities.enums.TransactionType;
import com.pedro.cashflow_api.services.TransactionService;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class TransactionResponseDTO {
    private Long id;
    private String description;
    private BigDecimal amount;
    private LocalDate date;
    private TransactionType type;
    private TransactionCategory category;

    public TransactionResponseDTO(
            Long id,
            String description,
            BigDecimal amount,
            LocalDate date,
            TransactionType type,
            TransactionCategory category
    ) {

        this.id = id;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.type = type;
        this.category = category;
    }

    public static TransactionResponseDTO transformIntoDTO(Transaction t) {
        return new TransactionResponseDTO(
                t.getId(),
                t.getDescription(),
                t.getAmount(),
                t.getDate(),
                t.getType(),
                t.getCategory());
    }

}


