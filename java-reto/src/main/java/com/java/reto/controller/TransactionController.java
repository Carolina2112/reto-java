package com.java.reto.controller;

import com.java.reto.dto.TransactionDTO;
import com.java.reto.dto.TransactionRequestDTO;
import com.java.reto.exception.InsufficientBalanceException;
import com.java.reto.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
	private final TransactionService transactionService;

	@Autowired
	public TransactionController(TransactionService transactionService) {
	    this.transactionService = transactionService;
	}

	@GetMapping
	public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
	    List<TransactionDTO> transactions = transactionService.getAllTransactions();
	    return ResponseEntity.ok(transactions);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long id) {
	    TransactionDTO transaction = transactionService.getTransactionById(id);
	    return ResponseEntity.ok(transaction);
	}

	@GetMapping("/account/{accountId}")
	public ResponseEntity<List<TransactionDTO>> getTransactionsByAccountId(@PathVariable Long accountId) {
	    List<TransactionDTO> transactions = transactionService.getTransactionsByAccountId(accountId);
	    return ResponseEntity.ok(transactions);
	}

	@GetMapping("/account/number/{accountNumber}")
	public ResponseEntity<List<TransactionDTO>> getTransactionsByAccountNumber(@PathVariable String accountNumber) {
	    List<TransactionDTO> transactions = transactionService.getTransactionsByAccountNumber(accountNumber);
	    return ResponseEntity.ok(transactions);
	}

	@PostMapping
	public ResponseEntity<?> createTransaction(@Valid @RequestBody TransactionRequestDTO requestDTO) {
	    try {
	        TransactionDTO createdTransaction = transactionService.createTransaction(requestDTO);
	        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
	    } catch (InsufficientBalanceException e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	    }
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTransaction(@PathVariable("id") Long id) {
	    transactionService.deleteTransaction(id);
	    return ResponseEntity.noContent().build();
	}
}