package com.pedro.cashflow_api.controllers;

import com.pedro.cashflow_api.dto.TransactionRequestDTO;
import com.pedro.cashflow_api.dto.TransactionResponseDTO;
import com.pedro.cashflow_api.dto.TransactionSummaryDTO;
import com.pedro.cashflow_api.entities.Transaction;
import com.pedro.cashflow_api.entities.enums.TransactionCategory;
import com.pedro.cashflow_api.entities.enums.TransactionType;
import com.pedro.cashflow_api.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(@Autowired TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getTransaction() {
        List<Transaction> allTransactions = transactionService.getTransactions();
        return new ResponseEntity<>(allTransactions, HttpStatus.ACCEPTED);
    }

    @GetMapping("/transactions/search")
    public ResponseEntity<List<Transaction>> findByCategory(
            @RequestParam(required = false) TransactionCategory category,
            @RequestParam(required = false) TransactionType type,
            @RequestParam(required = false) String keyword
    ) {
        List<Transaction> transactions = transactionService.search(category, type, keyword);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/transactions/summary")
    public ResponseEntity<TransactionSummaryDTO> caculateSummary() {
        TransactionSummaryDTO summary = transactionService.caculateSummary();
        return new ResponseEntity<>(summary, HttpStatus.OK);
    }


    @PostMapping("/transactions")
    public ResponseEntity<TransactionResponseDTO> createTransaction(@RequestBody TransactionRequestDTO transactionRequestDTO) {
        Transaction transactionRequestObj = transactionRequestDTO.transformIntoObject();
        Transaction transactionResponseObj = transactionService.saveTransaction(transactionRequestObj);
        return new ResponseEntity<>(TransactionResponseDTO.transformIntoDTO(transactionResponseObj), HttpStatus.CREATED);
    }

    @PutMapping("/transactions/{id}")
    public ResponseEntity<TransactionResponseDTO> updateTransaction(@PathVariable Long id,
                                                                    @RequestBody TransactionRequestDTO transactionRequestDTO) {
        Transaction transactionRequestObj = transactionRequestDTO.transformIntoObject();
        Transaction transactionResponseObj = transactionService.updateTransaction(id, transactionRequestObj);
        return new ResponseEntity<>(TransactionResponseDTO.transformIntoDTO(transactionResponseObj), HttpStatus.CREATED);
    }

    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
